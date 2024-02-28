package com.example.market.security.Filter;

import com.example.market.dto.member.MemberLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomJsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String DEFAULT_LOGIN_REQUEST_URL = "/login";
    private static final String HTTP_METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json";
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);
    private final ObjectMapper objectMapper;

    public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }

    // Header mediaType : form
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }
        MemberLoginDto.Request loginDto = objectMapper.readValue(
                StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), MemberLoginDto.Request.class);
        UsernamePasswordAuthenticationToken authRequest = getUsernamePasswordAuthenticationToken(loginDto);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(MemberLoginDto.Request loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        if (email == null || password == null) {
            throw new AuthenticationServiceException("E-mail or password is empty!");
        }
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}