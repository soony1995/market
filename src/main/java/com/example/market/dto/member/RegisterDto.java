package com.example.market.dto.member;

import com.example.market.domain.Cart;
import com.example.market.domain.Member;
import com.example.market.type.MemberRole;
import com.example.market.type.MemberStatus;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import reactor.util.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class RegisterDto {
    @NonNull
    private String email;

    @NonNull
    private String password;

    @Nullable
    private String location;

    public String encryptPassword() {
        return BCrypt.hashpw(this.password, BCrypt.gensalt());
    }

    public Member toEntity(Cart cart) {
        return Member.builder()
                .email(this.getEmail())
                .password(encryptPassword())
                .location(this.getLocation())
                .status(MemberStatus.ACTIVE)
                .memberRole(MemberRole.USER)
                .emailAuth(false)
                .emailAuthKey(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .cart(cart)
                .build();
    }
}
