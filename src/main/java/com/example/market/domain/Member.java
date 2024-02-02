package com.example.market.domain;

import com.example.market.dto.member.RegisterDto;
import com.example.market.type.MemberRole;
import com.example.market.type.MemberStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    @NonNull
    @Column(unique = true) // 동시에 같은 이름을 이용해 생성할 수 있기 때문에 unique 키 옵션을 지정해준다.
    private String email;

    @NonNull
    private String password;

    @Nullable
    private String location;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    // 일대일 관계에서는 호출이 많이 불리는 곳을 연관관계의 주인으로 둔다.
    // 연관관계의 주인은 외래키를 가지고 있다.
    // 연관관계의 주인 쪽에서 joinColumn을 사용한다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private boolean emailAuth;
    private String emailAuthKey;

    private LocalDateTime emailAuthAt;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Member createMember(RegisterDto dto, Cart cart) {
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .location(dto.getLocation())
                .status(MemberStatus.ACTIVE)
                .memberRole(MemberRole.USER)
                .emailAuth(false)
                .emailAuthKey(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .cart(cart)
                .build();
        return member;
    }

    // 이메일 인증을 처리하는 메서드
        public void authenticateEmail() {
            this.emailAuth = true;
            this.emailAuthAt = LocalDateTime.now();
        }
}
