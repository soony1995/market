package com.example.market.dto.item;

import com.example.market.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ItemRegisterDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Request {
        private int price;
        private String name;
        private int stock;
        private String description;

        public Item toEntity() {
            return Item.builder()
                    .name(this.name)
                    .price(this.price)
                    .stock(this.stock)
                    .description(this.description)
                    .build();
        }
    }
}
