package com.example.market.domain;

import com.example.market.dto.order.OrderModifyStatusDto;
import com.example.market.dto.order.OrdersDto;
import com.example.market.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public OrdersDto.Response convertToOrderDetailsDto() {
        return OrdersDto.Response.builder()
                .orderId(this.id)
                .orderedAt(this.getCreatedDate()) // baseEntity에서 getter를 세팅해주면 된다.
                .orderStatus(this.status)
                .build();
    }

    public OrderModifyStatusDto.Response convertToOrderModifyStatusDto() {
        return OrderModifyStatusDto.Response.builder()
                .result("성공!")
                .build();
    }

    public void modifyOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
