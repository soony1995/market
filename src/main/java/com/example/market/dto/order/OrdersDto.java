package com.example.market.dto.order;

import com.example.market.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class OrdersDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private long orderId;
        private LocalDateTime orderedAt;
        private OrderStatus orderStatus;
    }
}
