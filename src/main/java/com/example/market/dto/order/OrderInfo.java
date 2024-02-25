package com.example.market.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderInfo {
    public static  class Response{
        private long orderId;
        private String name;
        private  int count;
        private int orderPrice;

    }
}
