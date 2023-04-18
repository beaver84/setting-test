package com.example.settingtest.controller;

import com.example.settingtest.domain.Member;
import com.example.settingtest.dto.MemberFormDto;
import com.example.settingtest.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }

        try {
//            Member member = Member.createMember(memberFormDto, passwordEncoder);
//
//            public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
//                Member member = new Member();
//                member.setName(memberFormDto.getName());
//                member.setEmail(memberFormDto.getEmail());
//                member.setAddress(memberFormDto.getAddress());
//                String password = passwordEncoder.encode(memberFormDto.getPassword());
//                member.setPassword(password);
//                member.setRole(Role.USER);
//                return member;
//            }
//
//            memberService.join(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }
}
