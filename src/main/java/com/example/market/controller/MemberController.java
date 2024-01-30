package com.example.market.controller;

import com.example.market.domain.Member;
import com.example.market.dto.MemberDto;
import com.example.market.repository.MemberRepository;
import com.example.market.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/register")
    public String publicPage() {
        return "member/register";
    }

    @PostMapping(value = "/member/register")
    public String register(MemberDto request, Model model) {
        boolean res = memberService.register(request);
        model.addAttribute("result",res);
        // res의 결과에 따라 보여지는 view가 다르다.
        return "member/register";
    }
}
