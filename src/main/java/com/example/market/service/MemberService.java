package com.example.market.service;


import com.example.market.domain.Member;
import com.example.market.dto.MemberDto;
import com.example.market.repository.MemberRepository;
import com.fasterxml.classmate.MemberResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public boolean register(MemberDto dto) {
        // 이미 가입된 회원인지 체크하는 로직

        // 저장하는 로직
        memberRepository.save(Member.createMember(dto));

        // 이메일 발송 로직
        return true;
    }
}
