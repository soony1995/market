package com.example.market.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long id;

    // OneToOne의 fetch tpye의 기본값이 eager이다
    // 이유:즉, 부모 엔티티를 로드할 때 연관된 자식 엔티티들도 함께 즉시 로드됩니다
    @OneToOne
    private Member member;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
