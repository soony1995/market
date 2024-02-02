package com.example.market.config;


import com.example.market.service.MemberService;
import com.example.market.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration // TODO: 빈의 싱글톤 패턴을 보장해주는 어노테이션이다.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* 1. UserDetailsService을 상속받은 객체를 넘겨줘야 한다.
           2. impl에서 새로 상속받은 인터페이스를 구현해줘야 한다.
         */

        auth.userDetailsService(memberService)
                .passwordEncoder(getPasswordEncoder());

        super.configure(auth);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // TODO: csrf?
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/member/register",
                        "/member/email-auth"
                        , "/member/find/password")
                .permitAll();

        http.formLogin()
                .loginPage("/member/login")
                .failureHandler(getFailureHandler()) // TODO: 로그인이 실패했을 때의 핸들러
                .permitAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);


        super.configure(http);
    }
}
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
