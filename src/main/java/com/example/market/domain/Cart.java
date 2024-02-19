package com.example.market.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Cart extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long id;

    // OneToOne의 fetch tpye의 기본값이 eager이다
    // 이유:즉, 부모 엔티티를 로드할 때 연관된 자식 엔티티들도 함께 즉시 로드됩니다
//    @OneToOne(mappedBy = "cart",fetch = FetchType.LAZY)
//    private Member member;
}
