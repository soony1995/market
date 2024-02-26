package com.example.market.dto.item;

import com.example.market.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ItemInfoDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Request {
        private long id;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Response {
        private int price;
        private String itemName;
        private int stock;
        private String description;

        public static ItemInfoDto.Response fromEntity(Item item) {
            return Response.builder()
                    .price(item.getPrice())
                    .itemName(item.getName())
                    .stock(item.getStock())
                    .description(item.getDescription())
                    .build();
        }
    }
}
