package com.example.market.dto.order;

import com.example.market.domain.Order;
import com.example.market.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class OrderDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private long orderId;
        private LocalDateTime orderedAt;
        private OrderStatus orderStatus;

        public static OrderDto.Response fromEntity(Order order) {
            return Response.builder()
                    .orderId(order.getId())
                    .orderedAt(order.getCreatedDate()) // baseEntity에서 getter를 세팅해주면 된다.
                    .orderStatus(order.getStatus())
                    .build();
        }
    }
}
