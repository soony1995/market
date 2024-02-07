package com.example.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
