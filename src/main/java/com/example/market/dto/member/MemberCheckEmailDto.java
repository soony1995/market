package com.example.market.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class MemberCheckEmailDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String result;
    }
}
