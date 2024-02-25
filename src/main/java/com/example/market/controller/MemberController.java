package com.example.market.controller;

import com.example.market.dto.member.MemberRegister;
import com.example.market.service.MemberService;
import com.example.market.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/{version}")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<Object> register(@RequestBody MemberRegister.Request request, @PathVariable String version) {
        String authKey = memberService.register(request);
        return ResponseBuilder.buildOkResponse(authKey);
    }

    @GetMapping("/members/auth-check/{key}")
    public ResponseEntity<Object> authCheck(@PathVariable String key, @PathVariable String version) {
        memberService.authCheck(key);
        return ResponseBuilder.buildOkResponse();
    }


}
