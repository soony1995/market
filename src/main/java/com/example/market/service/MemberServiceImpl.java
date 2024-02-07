package com.example.market.service;


import com.example.market.component.JwtTokenProvider;
import com.example.market.component.MailComponent;
import com.example.market.domain.Member;
import com.example.market.dto.member.MemberLogin;
import com.example.market.dto.member.MemberRegister;
import com.example.market.exception.MemberException;
import com.example.market.repository.MemberRepository;
import com.example.market.type.ErrCode;
import com.example.market.type.MailFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MailComponent mailComponent;
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public void login(MemberLogin.Request request) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // set-cookie를 통해 저장
    }

    @Transactional
    @Override
    public String register(MemberRegister.Request request) {
        // 이미 가입된 회원인지 체크하는 로직
        memberRepository.findByEmail(request.getEmail()).ifPresent(member -> {
            throw new MemberException(ErrCode.ACCOUNT_ALREADY_REGISTERED);
        });
        // 저장
        Member member = memberRepository.save(request.toEntity());

        // 이메일 발송 로직
        return emailAuthorize(member.getEmail(), member.getEmailAuthKey(), MailFormat.SIGNUP_CONFIRMATION);
    }

    // 굳이 서비스 레이어에서 해야 할까?
    public void authCheck(String authKey) {
        memberRepository.findByEmailAuthKey(authKey).ifPresent(member -> {
            throw new MemberException(ErrCode.INVALID_AUTH_KEY);
        });
    }

    private String emailAuthorize(String userEmail, String authKey, MailFormat mailFormat) {
        String subject = mailFormat.getSubject();
        String text = mailFormat.getTextTemplate(authKey);
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
