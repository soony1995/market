package com.example.market.controller;

import com.example.market.security.dto.TokenDto;
import com.example.market.dto.member.MemberLogin;
import com.example.market.dto.member.MemberRegister;
import com.example.market.service.MemberService;
import com.example.market.utils.ResponseEntityBuilder;
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
    private final MemberService memberService;

    @PostMapping("/api/authorization")
    public ResponseEntity<Object> authorize(@RequestBody MemberLogin.Request request, HttpServletResponse response) {
        TokenDto tokens = memberService.authorize(request);

        addCookieToResponse(response, "accessToken", tokens.getAccessToken(), tokens.getExpiredTime());
        addCookieToResponse(response, "refreshToken", tokens.getRefreshToken(), tokens.getExpiredTime());

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

    private void addCookieToResponse(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
