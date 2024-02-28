package com.example.market.domain;

import com.example.market.dto.member.MemberCheckEmailDto;
import com.example.market.dto.member.MemberRegisterDto;
import com.example.market.type.MemberStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
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

    private String roles;

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

    public MemberCheckEmailDto.Response convertToMemberCheckEmailDto() {
        return MemberCheckEmailDto.Response.builder()
                .result("성공!")
                .build();
    }

    public MemberRegisterDto.Response convertToMemberRegisterDto() {
        return MemberRegisterDto.Response.builder()
                .emailAuthKey(this.emailAuthKey)
                .build();
    }

    public void markEmailAsVerified() {
        this.emailAuth = true;
        this.emailAuthAt = LocalDateTime.now();
    }
}
