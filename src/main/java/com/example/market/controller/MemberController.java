package com.example.market.controller;

import com.example.market.dto.member.MemberLogin;
import com.example.market.dto.member.MemberRegister;
import com.example.market.service.MemberServiceImpl;
import com.example.market.util.ResponseEntityBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;

    @PostMapping("/members/login")
    public ResponseEntity<Object> login(@RequestBody MemberLogin.Request request) {
        memberService.login(request);
        return ResponseEntityBuilder.buildOkResponse();
        // header-set-cookie를 통해서 access, refresh token을 추가하는 로직 필요
    }

    @PostMapping("/members/register")
    public ResponseEntity<Object> register(@RequestBody MemberRegister.Request request) {
        String authKey = memberService.register(request);
        return ResponseEntityBuilder.buildOkResponse(authKey);
    }

    @GetMapping("/members/auth/{authKey}")
    public ResponseEntity<Object> authCheck(@PathVariable String authKey){
        memberService.authCheck(authKey);
        return ResponseEntityBuilder.buildOkResponse();
    }


//
//    @GetMapping("/member/find/password")
//    public String findPassword() {
//        return "member/find_password";
//    }
//
//    @PostMapping("/member/find/password")
//    public String findPasswordSubmit(ResetPasswordDto request, Model model) {
//        // TODO: 예외 처리 해야함.
//        boolean res = memberService.sendPasswordResetLink(request);
//
//        model.addAttribute("result", res);
//
//        // TODO: 리다이렉트를 하는 방법: return "redirect:/";
//        return "member/find_password_result";
//    }
//
//    @GetMapping("/member/email-auth")
//    public String emailAuth(HttpServletRequest request, Model model) {
//        String id = request.getParameter("id");
//
//        boolean res = memberService.validateEmailConfirmation(id);
//        model.addAttribute("result", res);
//
//        return "member/email_auth";
//    }
//
//    @GetMapping("member/reset-password")
//    public String resetPassword(HttpServletRequest request, Model model) {
//        String id = request.getParameter("id");
//
//        boolean res = memberService.validatePasswordResetLink(id);
//        model.addAttribute("result", res);
//
//        return "member/reset-password";
//    }
}
