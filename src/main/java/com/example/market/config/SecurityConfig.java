package com.example.market.config;

import com.example.market.Filter.JwtAuthenticationFilter;
import com.example.market.component.JwtTokenProvider;
import com.example.market.security.LoginProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.AuthProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginProvider loginProvider;

    // AuthenticationProvider을 구현한 provider를 만들고
    // 아이디 비번을 Header Basic 형식으로 인코딩해서 넣어주면 됨
    // 패스워드 암호화
    // SuccessHandler / FailureHandler
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/members").permitAll()
                .antMatchers("/api/members/auth-check/{id}").permitAll()
                .antMatchers("/api/authorization").permitAll()
                .anyRequest().authenticated()

                .and()
                .authenticationProvider(loginProvider).formLogin().loginProcessingUrl("authorize").successHandler(loginProcessingUrl).failureHandler(loginProcessingUrl)
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}