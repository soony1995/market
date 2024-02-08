package com.example.market.controller;

import com.example.market.dto.TokenDto;
import com.example.market.dto.member.MemberLogin;
import com.example.market.dto.member.MemberRegister;
import com.example.market.service.MemberServiceImpl;
import com.example.market.util.ResponseEntityBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;

    @PostMapping("/authorization")
    public ResponseEntity<Object> authorize(@RequestBody MemberLogin.Request request, HttpServletResponse response) {
        TokenDto tokens = memberService.authorize(request);
        Cookie accessToken = new Cookie("accessToken", tokens.getAccessToken());
        // 쿠키 설정 (예: 24시간 후 만료)
        accessToken.setMaxAge(24 * 60 * 60);
        accessToken.setHttpOnly(true); // JavaScript를 통한 접근 방지
        accessToken.setPath("/"); // 쿠키가 전송될 수 있는 경로

        Cookie refreshToken = new Cookie("refreshToken", tokens.getAccessToken());
        // 쿠키 설정 (예: 24시간 후 만료)
        refreshToken.setMaxAge(24 * 60 * 60);
        refreshToken.setHttpOnly(true); // JavaScript를 통한 접근 방지
        refreshToken.setPath("/"); // 쿠키가 전송될 수 있는 경로

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        return ResponseEntityBuilder.buildOkResponse();
        // header-set-cookie를 통해서 access, refresh token을 추가하는 로직 필요
    }

    @PostMapping("/members")
    public ResponseEntity<Object> register(@RequestBody MemberRegister.Request request) {
        String authKey = memberService.register(request);
        return ResponseEntityBuilder.buildOkResponse(authKey);
    }

    @GetMapping("/members/auth-check/{key}")
    public ResponseEntity<Object> authCheck(@PathVariable String key) {
        memberService.authCheck(key);
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
