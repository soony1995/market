package com.example.market.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailFormat {
    SIGNUP_CONFIRMATION("홈페이지 가입을 축하드립니다.", "회원가입 인증 메일입니다. 링크를 클릭하여 가입을 완료하세요. <a href='http://localhost:8080/member/email-auth?id=%s'>가입완료</a>"),
    PASSWORD_RESET("비밀번호 재설정 안내", "비밀번호 재설정 메일입니다. 링크를 클릭하여 비밀번호를 재설정하세요. <a href='http://localhost:8080/member/reset-password?id=%s'>비밀번호 재설정</a>");

    private final String subject;
    private final String textTemplate;

    public String getTextTemplate(String authKey) {
        return String.format(textTemplate, authKey);
    }
}
