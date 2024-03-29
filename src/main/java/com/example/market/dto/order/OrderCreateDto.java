package com.example.market.dto.order;

import com.example.market.domain.Item;
import com.example.market.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


public class OrderCreateDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private long cartId;
        private String location;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String itemName;
        private int orderPrice;
        private int count;
        private String location;
    }
}
