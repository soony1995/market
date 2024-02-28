package com.example.market.service;

import com.example.market.component.MailComponent;
import com.example.market.domain.Member;
import com.example.market.dto.member.MemberCheckEmailDto;
import com.example.market.dto.member.MemberRegisterDto;
import com.example.market.exception.CustomException;
import com.example.market.repository.MemberRepository;
import com.example.market.type.ErrCode;
import com.example.market.type.MailFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MailComponent mailComponent;

    @Transactional
    public MemberRegisterDto.Response addMember(MemberRegisterDto.Request request) {
        memberRepository.findByEmail(request.getEmail()).ifPresent(member -> {
            throw new CustomException(ErrCode.MEMBER_ALREADY_REGISTERED);
        });
        Member member = memberRepository.save(request.convertToMember());
        emailAuthorize(member.getEmail(), member.getEmailAuthKey());

        return member.convertToMemberRegisterDto();
    }

    @Transactional(readOnly = true)
    public MemberCheckEmailDto.Response checkMemberEmail(String authKey) {
        Member member = memberRepository.findByEmailAuthKey(authKey)
                .orElseThrow(() -> new CustomException(ErrCode.MEMBER_INVALID_AUTH_KEY));
        member.markEmailAsVerified();

        return member.convertToMemberCheckEmailDto();
    }

    private void emailAuthorize(String userEmail, String authKey) {
        String subject = MailFormat.SIGNUP_CONFIRMATION.getSubject();
        String text = MailFormat.SIGNUP_CONFIRMATION.getTextTemplate(authKey);
        mailComponent.sendMail(userEmail, subject, text);
    }
}
