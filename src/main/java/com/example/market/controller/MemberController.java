package com.example.market.controller;

import com.example.market.dto.member.RegisterDto;
import com.example.market.dto.member.ResetPasswordDto;
import com.example.market.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;

    @RequestMapping("/member/login") // post와 get을 동시에 받을 수 있다.
    public String loginPage() {
        return "member/login";
    }

    @GetMapping("/member/register")
    public String publicPage() {
        return "member/register";
    }

    @PostMapping("/member/register")
    public String register(RegisterDto request, Model model) {
        // TODO: dto를 넘겨주는 게 나은 가? 값을 풀어서 하나씩 넘겨주는 게 나을 까?
        boolean res = memberService.register(request);
        model.addAttribute("result", res);
        // res의 결과에 따라 보여지는 view가 다르다.
        return "member/register_result";
    }

    @GetMapping("/member/find/password")
    public String findPassword() {
        return "member/find_password";
    }

    @PostMapping("/member/find/password")
    public String findPasswordSubmit(ResetPasswordDto request, Model model) {
        // TODO: 예외 처리 해야함.
        boolean res = memberService.sendPasswordResetLink(request);

        model.addAttribute("result", res);

        // TODO: 리다이렉트를 하는 방법: return "redirect:/";
        return "member/find_password_result";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");

        boolean res = memberService.validateEmailConfirmation(id);
        model.addAttribute("result", res);

        return "member/email_auth";
    }

    @GetMapping("member/reset-password")
    public String resetPassword(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");

        boolean res = memberService.validatePasswordResetLink(id);
        model.addAttribute("result", res);

        return "member/reset-password";
    }
}
