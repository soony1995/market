package com.example.market.dto.cart;

import com.example.market.domain.CartItem;
import com.example.market.domain.Item;
import com.example.market.dto.item.ItemInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CartQueryItems {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Response {
        private String name;
        private int count;
        private int price;

        public static CartQueryItems.Response fromEntity(CartItem cartItem) {
            return Response.builder()
                    .name(cartItem.getItem().getName())
                    .price(cartItem.getPrice())
                    .count(cartItem.getCount())
                    .build();
        }
    }
}
