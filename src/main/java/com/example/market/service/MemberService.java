package com.example.market.service;

import com.example.market.dto.member.MemberLogin;
import com.example.market.dto.member.MemberRegister;

public interface MemberService {
    String register(MemberRegister.Request request);

    void login(MemberLogin.Request request);

    void authCheck(String authKey);

    /**
     계정인증
     */
//    boolean validateEmailConfirmation(String emailAuthKey);
//
//    /**
//     * 비밀번호 초기화
//     */
//    boolean sendPasswordResetLink(ResetPasswordDto request);
//
//    boolean validatePasswordResetLink(String id);
}
