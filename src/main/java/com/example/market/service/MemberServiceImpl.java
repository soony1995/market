package com.example.market.service;


import com.example.market.component.MailComponent;
import com.example.market.domain.Cart;
import com.example.market.domain.Member;
import com.example.market.dto.member.RegisterDto;
import com.example.market.dto.member.ResetPasswordDto;
import com.example.market.exception.MemberNotEmailAuthException;
import com.example.market.repository.MemberRepository;
import com.example.market.type.MailFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MailComponent mailComponent;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    @Override
    public boolean register(RegisterDto dto) {
        // 이미 가입된 회원인지 체크하는 로직
        Optional<Member> optionalMember = memberRepository.findByEmail(dto.getEmail());
        if (optionalMember.isPresent()) {
            return false;
        }
        // Cart 객체 생성
        Cart cart = new Cart();

        // Member 객체 생성
        Member member = dto.toEntity(cart);

        memberRepository.save(member);

        // 이메일 발송 로직
        emailAuthorize(member.getEmail(), member.getEmailAuthKey(), MailFormat.SIGNUP_CONFIRMATION);

        return true;
    }

    @Transactional
    @Override
    public boolean validateEmailConfirmation(String emailAuthKey) {
        Optional<Member> findMember = memberRepository.findByEmailAuthKey(emailAuthKey);

        // TODO: isEmpty는 null 체크를 하지 않기 때문에 null 체크를 먼저 하도록 하자.
        if (findMember.isEmpty()) {
            return false;
        }

        Member member = findMember.get();
        /* TODO: jpa dirty checking을 이용해 받아온 객체의 값을 변경하는 것만으로도
               저장소에도 변경이 가능하게 해준다.
         */
        member.authenticateEmail();

        return true;
    }

    @Transactional
    @Override
    public boolean sendPasswordResetLink(ResetPasswordDto request) {
        Optional<Member> optionalMember = memberRepository.findByEmail(request.getUserEmail());
        if (optionalMember.isEmpty()) {
            throw new UsernameNotFoundException("이메일이 존재 하지 않습니다.");
        }
        // ttl을 설정한 uuid를 redis에 저장
        String key = UUID.randomUUID().toString();
        // Set에 사용자 이메일 추가
        redisTemplate.opsForSet().add(key, request.getUserEmail());

        // 해당 키에 대해 TTL 설정
        redisTemplate.expire(key, Duration.ofHours(1));

        // mailSender에 uuid를 포함해 send
        emailAuthorize(request.getUserEmail(), key, MailFormat.PASSWORD_RESET);

        return true;
    }

    @Override
    public boolean validatePasswordResetLink(String id) {
        //
        return false;
    }

    private void emailAuthorize(String userEmail, String authKey, MailFormat mailFormat) {
        String subject = mailFormat.getSubject();
        String text = mailFormat.getTextTemplate(authKey);
        mailComponent.sendMail(userEmail, subject, text);
    }

    // spring security에 필요한 메서드 오버라이딩
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(username);

        if (optionalMember.isEmpty()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if (!member.isEmailAuth()) {
            throw new MemberNotEmailAuthException("이메일 인증 후에 로그인을 해주세요");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER_ROLE"));

        return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }

}
