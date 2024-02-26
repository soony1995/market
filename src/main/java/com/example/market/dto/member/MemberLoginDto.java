package com.example.market.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class MemberLoginDto {

    @Getter
    @NoArgsConstructor // 역 직렬화를 위해 기본생성자를 생성해야 한다. jackson에서 요구함.
    @AllArgsConstructor // builder 패턴을 사용하기 위해서 사용한다.
    @Builder // 역 직렬화를 위해 setter 또는 builder 패턴을 사용해야 한다.
    public static class Request {
        private String email;
        private String password;
    }
}
