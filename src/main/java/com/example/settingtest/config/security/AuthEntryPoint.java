package com.example.settingtest.config.security;

import com.google.gson.Gson;
import com.example.settingtest.config.exception.ApiExceptionInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        ApiExceptionInfo apiException = new ApiExceptionInfo();
        apiException.setHttpStatus(HttpStatus.UNAUTHORIZED);
        apiException.setMessage("인증되지 않은 사용자입니다.");
        apiException.setSuccess(false);

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        OutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(new Gson().toJson(apiException).getBytes());
        outputStream.flush();
    }
}

