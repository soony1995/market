package com.example.market.security.handler;

import com.example.market.security.component.JwtTokenProvider;
import com.example.market.security.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {TokenDto tokens = jwtService.generateToken(authentication);
        jwtService.addCookieToResponse(response, "accessToken", tokens.getAccessToken(), tokens.getExpiredTime());
        jwtService.addCookieToResponse(response, "refreshToken", tokens.getRefreshToken(), tokens.getExpiredTime());
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
