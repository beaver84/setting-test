package com.example.settingtest.service;

import com.example.settingtest.config.exception.ApiException;
import com.example.settingtest.config.security.AuthEncrypter;
import com.example.settingtest.config.security.AuthProvider;
import com.example.settingtest.domain.Member;
import com.example.settingtest.dto.LoginFormDto;
import com.example.settingtest.repository.jpa.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.settingtest.config.security.TestAuthenticationToken;
import com.example.settingtest.config.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberApiService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;
    private final AuthEncrypter authEncrypter;
    private final AuthProvider authProvider;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Member login(LoginFormDto loginForm, HttpServletRequest request, HttpServletResponse response) {
        Member member = memberJpaRepository.findByEmail(loginForm.getEmail());

        if(Objects.isNull(member)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다.");
        }

//        //암호화된 입력된 암호, DB에 저장된 암호
//        String inputPassword = loginForm.getPassword();
//        String storedPassword = member.getPassword();
//
//        //복호화된 입력된 암호, DB에 저장된 암호
//        String decryptedInputPassword = authEncrypter.decrypt(inputPassword);
//        String decryptStoredPassword = authEncrypter.decrypt(storedPassword);

        //클라이언트에서 온 암호를 복호화하여 DB에 저장된 암호를 복호화 한것과 비교한다.
        if (StringUtils.equals(authEncrypter.decrypt(member.getPassword()), authEncrypter.decrypt(loginForm.getPassword()))) {
            //멤버 인증하여 토큰 반환
            Authentication authentication = authProvider.authenticate(
                    new TestAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtTokenProvider.generateJwtToken(member);
            response.addHeader("Authorization", "Bearer " + jwtToken);

            //토큰이 있으면 account에 토큰정보를 추가하여 출력
            if (StringUtils.isNotEmpty(loginForm.getToken())) {
                member.setToken(loginForm.getToken());
            }
            log.debug(loginForm.getEmail() + " 로 로그인에 성공하였습니다.");
            return member;
        } else {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberJpaRepository.findByEmail(email);

        if (Objects.isNull(member)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디입니다.");
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
