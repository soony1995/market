package com.example.market.security;

import com.example.market.dto.TokenDto;
import com.example.market.util.ResponseEntityBuilder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.security.AuthProvider;

@Component
public class LoginProvider extends AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Header Basic 인증
        TokenDto tokens = memberService.authorize(request);
        Cookie accessToken = new Cookie("accessToken", tokens.getAccessToken());
        // 쿠키 설정 (예: 24시간 후 만료)
        accessToken.setMaxAge(24 * 60 * 60);
        accessToken.setHttpOnly(true); // JavaScript를 통한 접근 방지
        accessToken.setPath("/"); // 쿠키가 전송될 수 있는 경로

        Cookie refreshToken = new Cookie("refreshToken", tokens.getAccessToken());
        // 쿠키 설정 (예: 24시간 후 만료)
        refreshToken.setMaxAge(24 * 60 * 60);
        refreshToken.setHttpOnly(true); // JavaScript를 통한 접근 방지
        refreshToken.setPath("/"); // 쿠키가 전송될 수 있는 경로

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        return ResponseEntityBuilder.buildOkResponse();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
