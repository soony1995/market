package com.example.market.service;

import com.example.market.dto.member.RegisterDto;
import com.example.market.dto.member.ResetPasswordDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    boolean register(RegisterDto dto);

    /**
        계정인증
     */
    boolean validateEmailConfirmation(String emailAuthKey);


    /**
     * 비밀번호 초기화
     */
    boolean sendPasswordResetLink(ResetPasswordDto request);

    boolean validatePasswordResetLink(String id);
}
