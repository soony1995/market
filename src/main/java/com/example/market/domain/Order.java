package com.example.market.domain;

import com.example.market.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id;

    // 양방향 연관관계를 가진다. 연관관계의 주인을 명시해줘야 한다.
    // foreign key가 있는 곳을 주인으로 설정한다.
    // member 테이블을 변경할 경우 order 테이블이 변할 수 있다.
    // 값의 변경은 이 클래스를 통해서만 변경한다.
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    public void modifyOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
