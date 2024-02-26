package com.example.market.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private final int expiredTime = 24 * 60 * 60;
}
