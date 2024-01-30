package com.example.market.domain;

import com.example.market.dto.MemberDto;
import com.example.market.type.MemberRole;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @Nullable
    private String location;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private String status;

    // 일대일 관계에서는 호출이 많이 불리는 곳을 연관관계의 주인으로 둔다.
    // 연관관계의 주인은 외래키를 가지고 있다.
    // 연관관계의 주인 쪽에서 joinColumn을 사용한다.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Member createMember(MemberDto dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .location(dto.getLocation())
                .build();
    }
}
