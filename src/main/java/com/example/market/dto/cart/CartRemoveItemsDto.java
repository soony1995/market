package com.example.market.dto.cart;

import com.example.market.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CartRemoveItemsDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private List<Long> itemId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String itemName;
        private int price;
        private int count;
    }
}
