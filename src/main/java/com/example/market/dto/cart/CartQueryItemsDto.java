package com.example.market.dto.cart;

import com.example.market.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CartQueryItemsDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Response {
        private long cartId;
        private String name;
        private int count;
        private int price;
    }
}
