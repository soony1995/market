package com.example.market.domain;

import com.example.market.dto.order.OrderCreateDto;
import com.example.market.dto.order.OrderItemsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;

    private String location;

    public OrderCreateDto.Response convertToOrderCreateDto() {
        return OrderCreateDto.Response.builder()
                .itemName(this.item.getName())
                .orderPrice(this.orderPrice)
                .count(this.count)
                .location(this.location)
                .build();
    }

    public OrderItemsDto.Response convertToOrderItemsDto() {
        return OrderItemsDto.Response.builder()
                .userEmail(order.getMember().getEmail())
                .orderId(order.getId())
                .itemId(this.getItem().getId())
                .itemName(this.getItem().getName())
                .orderPrice(this.orderPrice)
                .count(this.count)
                .location(this.location)
                .build();
    }
}
