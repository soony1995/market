package com.example.market.dto.order;

import com.example.market.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrderModifyStatus {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        OrderStatus orderStatus;
    }
}
