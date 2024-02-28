package com.example.market.dto.order;

import com.example.market.domain.Order;
import com.example.market.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class OrderItemsDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String userEmail;
        private long orderId;
        private long itemId;
        private String itemName;
        private int orderPrice;
        private int count;
        private String location;
    }
}