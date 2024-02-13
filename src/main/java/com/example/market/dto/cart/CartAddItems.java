package com.example.market.dto.cart;

import com.example.market.domain.Cart;
import com.example.market.domain.CartItem;
import com.example.market.domain.Item;
import lombok.*;

import java.time.LocalDateTime;

public class CartAddItems {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private long itemId;
        private int count;
        private int price;

        public CartItem toEntity(Cart cart, Item item) {
            return CartItem.builder()
                    .cartCount(this.count)
                    .cartPrice(this.price)
                    .item(item)
                    .cart(cart)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
    }
}
