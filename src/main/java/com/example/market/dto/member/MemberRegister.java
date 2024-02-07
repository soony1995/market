package com.example.market.dto.member;

import com.example.market.domain.Cart;
import com.example.market.domain.Member;
import com.example.market.type.MemberRole;
import com.example.market.type.MemberStatus;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import reactor.util.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MemberRegister {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Request {
        @NonNull
        private String email;

        @NonNull
        private String password;

        @Nullable
        private String location;

        public String encryptPassword() {
            return BCrypt.hashpw(this.password, BCrypt.gensalt());
        }

        public Member toEntity() {

//            List<String> roles = new ArrayList<>();
//            roles.add(MemberRole.USER.toString());
            Cart cart = new Cart();

            return Member.builder()
                    .email(this.getEmail())
                    .password(encryptPassword())
                    .location(this.getLocation())
                    .status(MemberStatus.ACTIVE)
                    .roles(MemberRole.USER.toString())
                    .emailAuth(false)
                    .emailAuthKey(UUID.randomUUID().toString())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .cart(cart)
                    .build();
        }
    }


}
