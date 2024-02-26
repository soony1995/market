package com.example.market.dto.cart;

import com.example.market.domain.Cart;
import com.example.market.domain.CartItem;
import com.example.market.domain.Item;
import lombok.*;

public class CartAddItemsDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private long itemId;
        private int count;

        public CartItem toEntity(Cart cart, Item item) {
            return CartItem.builder()
                    .count(this.count)
                    .item(item)
                    .cart(cart)
                    .price(item.getPrice() * this.count)
                    .build();
        }
    }
}
