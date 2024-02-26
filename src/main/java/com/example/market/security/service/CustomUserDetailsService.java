package com.example.market.security.service;

import com.example.market.domain.Member;
import com.example.market.repository.MemberRepository;
import com.example.market.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(username);
        if (member.isEmpty()) {
            throw new UsernameNotFoundException("아이디를 찾을 수 없습니다.");
        }
        return new CustomUserDetails(member.get());
    }
}