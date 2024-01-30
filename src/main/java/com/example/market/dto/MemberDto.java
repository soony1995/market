package com.example.market.dto;

import com.example.market.domain.Member;
import com.example.market.type.MemberRole;
import lombok.*;
import reactor.util.annotation.Nullable;

@Builder
@AllArgsConstructor
@Getter
public class MemberDto {
    @NonNull
    private String email;

    @NonNull
    private String password;

    @Nullable
    private String location;

    public Member toEntity(){
        return Member.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .location(this.getLocation())
                .memberRole(MemberRole.USER)
                .build();
    }

}
