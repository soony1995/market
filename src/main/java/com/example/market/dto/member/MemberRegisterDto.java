package com.example.market.dto.member;

import com.example.market.domain.Cart;
import com.example.market.domain.Member;
import com.example.market.type.MemberRole;
import com.example.market.type.MemberStatus;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.util.annotation.Nullable;

import java.util.UUID;


public class MemberRegisterDto {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @RequiredArgsConstructor
    public static class Request {
        private PasswordEncoder passwordEncoder;
        @NonNull
        private String email;

        @NonNull
        private String password;

        @Nullable
        private String location;

        public Member convertToMember() {
            Cart cart = new Cart();
            return Member.builder()
                    .email(this.getEmail())
                    .password(passwordEncoder.encode(this.getPassword()))
                    .location(this.getLocation())
                    .status(MemberStatus.ACTIVE)
                    .roles(MemberRole.USER.toString())
                    .emailAuth(false)
                    .emailAuthKey(UUID.randomUUID().toString())
                    .cart(cart)
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String emailAuthKey;
    }
}
