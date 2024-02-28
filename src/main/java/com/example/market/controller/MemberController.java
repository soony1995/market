package com.example.market.controller;

import com.example.market.dto.member.MemberCheckEmailDto;
import com.example.market.dto.member.MemberRegisterDto;
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
    public ResponseEntity<MemberRegisterDto.Response> memberAdd(@RequestBody MemberRegisterDto.Request request, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(memberService.addMember(request));
    }

    @GetMapping("/members/auth-check/{key}")
    public ResponseEntity<MemberCheckEmailDto.Response> MemberEmailCheck(@PathVariable String key, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(memberService.checkMemberEmail(key));
    }
}
