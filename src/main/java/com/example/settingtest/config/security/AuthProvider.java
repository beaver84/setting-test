package com.example.settingtest.config.security;

import com.example.settingtest.config.exception.ApiException;
import com.example.settingtest.constant.Role;
import com.example.settingtest.domain.Member;
import com.example.settingtest.repository.jpa.MemberJpaRepository;
import com.example.settingtest.repository.cache.CacheRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthProvider implements AuthenticationProvider {
    private static final int LOGIN_SESSION_EXTEND_MINUTES = 120;

    private final Logger log = LogManager.getLogger(this.getClass());

    private final CacheRepository cacheRepository;
    private final AuthEncrypter authEncrypter;
    private final MemberJpaRepository memberJpaRepository;

    public AuthProvider(CacheRepository cacheRepository,
                        AuthEncrypter authEncrypter,
                        MemberJpaRepository memberJpaRepository) {
        this.cacheRepository = cacheRepository;
        this.authEncrypter = authEncrypter;
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object principal = authentication.getPrincipal();

        if (Objects.isNull(principal)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "아이디를 입력해주세요.");
        }

        Member member = memberJpaRepository.findByEmail(principal.toString());

        if (Objects.isNull(member)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디입니다.");
        }

        String decryptInputPassword = authEncrypter.decrypt(authentication.getCredentials().toString());
        String decryptSavedPassword = authEncrypter.decrypt(member.getPassword());

        if (!StringUtils.equals(decryptInputPassword, decryptSavedPassword)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        String sessionKey = "";
        if (member.getRole() == Role.ADMIN) {
            sessionKey = "ADMIN:";
        } else {
            sessionKey = "USER:";
        }

        String cacheKey = sessionKey + member.getEmail();

        // MOON-303 중복로그인 방지 임시코드
//        if (StringUtils.isNotEmpty(cacheRepository.getValue(cacheKey))) {
//            throw new ApiException(HttpStatus.CONFLICT, "중복로그인이 감지되었습니다.");
//        }

        cacheRepository.setValue(cacheKey, String.valueOf(member.getId()), LOGIN_SESSION_EXTEND_MINUTES);

        return new TestAuthenticationToken(member.getEmail(), member.getPassword(), member.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
