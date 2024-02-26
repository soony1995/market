package com.example.market.service;

import com.example.market.component.MailComponent;
import com.example.market.domain.Member;
import com.example.market.dto.member.MemberRegisterDto;
import com.example.market.exception.MemberException;
import com.example.market.repository.MemberRepository;
import com.example.market.type.ErrCode;
import com.example.market.type.MailFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MailComponent mailComponent;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String addMember(MemberRegisterDto.Request request) {

        // 이미 가입된 회원인지 체크
        memberRepository.findByEmail(request.getEmail()).ifPresent(member -> {
            throw new MemberException(ErrCode.ACCOUNT_ALREADY_REGISTERED);
        });

        Member member = memberRepository.save(request.toEntity(passwordEncoder));

        // TODO: 이메일 재발송 로직에 대한 처리도 필요함.
        return emailAuthorize(member.getEmail(), member.getEmailAuthKey());
    }

    @Transactional(readOnly = true)
    public String checkMemberEmail(String authKey) {
        // 키를 이용해 찾기
        Optional<Member> findMember = memberRepository.findByEmailAuthKey(authKey);

        // 만약 존재하지 않을 경우 에러 발생
        if (findMember.isEmpty()) {
            throw new MemberException(ErrCode.INVALID_AUTH_KEY);
        }

        // member의 값을 업데이트 해준다.
        findMember.get().markEmailAsVerified();

        return "성공!";
    }

    private String emailAuthorize(String userEmail, String authKey) {

        String subject = MailFormat.SIGNUP_CONFIRMATION.getSubject();
        String text = MailFormat.SIGNUP_CONFIRMATION.getTextTemplate(authKey);

        mailComponent.sendMail(userEmail, subject, text);
        return authKey;
    }


//
//    @Transactional
//    @Override
//    public boolean validateEmailConfirmation(String emailAuthKey) {
//        Optional<Member> findMember = memberRepository.findByEmailAuthKey(emailAuthKey);
//
//        // TODO: isEmpty는 null 체크를 하지 않기 때문에 null 체크를 먼저 하도록 하자.
//        if (findMember.isEmpty()) {
//            return false;
//        }
//
//        Member member = findMember.get();
//        /* TODO: jpa dirty checking을 이용해 받아온 객체의 값을 변경하는 것만으로도
//               저장소에도 변경이 가능하게 해준다.
//         */
//        member.authenticateEmail();
//
//        return true;
//    }
//
//    @Transactional
//    @Override
//    public boolean sendPasswordResetLink(ResetPasswordDto request) {
//        Optional<Member> optionalMember = memberRepository.findByEmail(request.getUserEmail());
//        if (optionalMember.isEmpty()) {
//            throw new UsernameNotFoundException("이메일이 존재 하지 않습니다.");
//        }
//        // ttl을 설정한 uuid를 redis에 저장
//        String key = UUID.randomUUID().toString();
//        // Set에 사용자 이메일 추가
//        redisTemplate.opsForSet().add(key, request.getUserEmail());
//
//        // 해당 키에 대해 TTL 설정
//        redisTemplate.expire(key, Duration.ofHours(1));
//
//        // mailSender에 uuid를 포함해 send
//        emailAuthorize(request.getUserEmail(), key, MailFormat.PASSWORD_RESET);
//
//        return true;
//    }
//
//    @Override
//    public boolean validatePasswordResetLink(String id) {
//        //
//        return false;
//    }
//

}
