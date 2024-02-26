package com.example.market.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CartDeleteItemsDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private List<Long> itemId;
    }
}
