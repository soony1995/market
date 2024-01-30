//package com.example.market.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(authorizeConfig -> {
//                    authorizeConfig.antMatchers("/private").authenticated();
//                    authorizeConfig.anyRequest().permitAll();
//                })
//                .formLogin(Customizer.withDefaults()) // 로그인을 하려면 form 형식으로 보내야 한다.!
//                .oauth2Login(Customizer.withDefaults()) // oauth
//                .build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager( // 유저 정보 넘겨줌.
//                User.builder()
//                        .username("user")
//                        .password("{noop}password")
//                        .authorities("ROLE_user")
//                        .build()
//        );
//    }
//}
