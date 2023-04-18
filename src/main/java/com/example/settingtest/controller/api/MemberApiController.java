package com.example.settingtest.controller.api;

import com.example.settingtest.domain.Member;
import com.example.settingtest.dto.LoginFormDto;
import com.example.settingtest.service.MemberApiService;
import com.example.settingtest.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberApiService memberApiService;

    @PostMapping("/v1/account/login")
    public Member studentLogin(
            @RequestBody @Valid LoginFormDto loginFormDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return memberApiService.login(loginFormDto, request, response);
    }
}
