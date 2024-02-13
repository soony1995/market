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

    @PostMapping("/api/authorization")
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
    }

    @PostMapping("/api/members")
    public ResponseEntity<Object> register(@RequestBody MemberRegister.Request request) {
        String authKey = memberService.register(request);
        return ResponseEntityBuilder.buildOkResponse(authKey);
    }

    @GetMapping("/api/members/auth-check/{key}")
    public ResponseEntity<Object> authCheck(@PathVariable String key) {
        memberService.authCheck(key);
        return ResponseEntityBuilder.buildOkResponse();
    }
}
