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
    // UsernamePasswordAuthenticationFilter는 form 로그인 시에 최적화된 클래스이다.
    // json 방식의 로그인을 선택하기 때문에 추상 필터 클래스를 이용해 구현해주어야 한다.

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/login";
    private static final String HTTP_METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json";
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);
    private final ObjectMapper objectMapper;

    public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER); // 위에서 설정한 "login" + POST로 온 요청을 처리하기 위해 설정
        this.objectMapper = objectMapper;
    }

    // Header mediaType : form
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        // servlet request로 받아온 username, password를 내가 만든 dto에 매핑하는 코드
        MemberLoginDto.Request loginDto = objectMapper.readValue(
                StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), MemberLoginDto.Request.class);

        UsernamePasswordAuthenticationToken authRequest = getUsernamePasswordAuthenticationToken(loginDto);

        // 해당 토큰을 AuthenticationManager에게 인증을 요청한다.
        // 여기서 알 수 있는 부분은 AuthenticationManager에서 DB에서 username에 해당하는 로우를 가져와 비교 하는 로직이 있어야 함을 알 수 있다.
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(MemberLoginDto.Request loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        if (email == null || password == null) {
            throw new AuthenticationServiceException("E-mail or password is empty!");
        }

        // 이메일과 패스워드를 이용해 UsernamePasswordAuthenticationToken 객체를 만들어 request로 받아온 username/password만! 넣어준다.
        // 아직 인증은 하지 않은 상태
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}