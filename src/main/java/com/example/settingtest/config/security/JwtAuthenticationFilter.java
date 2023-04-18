package com.example.settingtest.config.security;

import com.google.gson.Gson;
import com.example.settingtest.config.exception.ApiException;
import com.example.settingtest.domain.Member;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final Gson gson;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.gson = new Gson();
    }

    /**
     * 요청에 대한 권한을 filter에서 체크
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String header = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isNotEmpty(header)) {
            String token = jwtTokenProvider.getTokenFromHeader(header);
            setAuthentication(token, request, response);
        } else {
            Cookie[] cookies = httpServletRequest.getCookies();
            if (ArrayUtils.isNotEmpty(cookies)) {
                for (Cookie cookie : cookies) {
                    if (StringUtils.equals(cookie.getName(), "auth")) {
                        String token = cookie.getValue();
                        setAuthentication(token, request, response);
                        break;
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }

    private void setAuthentication(String token, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (jwtTokenProvider.isValidToken(token)) {
            Claims claims = jwtTokenProvider.getClaimsFormToken(token);
            Member member = gson.fromJson(claims.get("claims").toString(), Member.class);
            String jwtToken = jwtTokenProvider.generateJwtToken(member);

            jwtTokenProvider.extendSession(jwtToken);
            Authentication authentication = jwtTokenProvider.createAuthenticationFromToken(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            httpServletResponse.addHeader("Authorization", jwtToken);
        } else {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "승인되지 않은 요청입니다.");
        }
    }
}
