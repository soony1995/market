package com.example.market.dto.order;

import com.example.market.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrderModifyStatusDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        OrderStatus orderStatus;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private String result;
    }
}
