package com.example.market.controller;

import com.example.market.dto.cart.CartAddItems;
import com.example.market.service.CartService;
import com.example.market.util.ResponseEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/api/carts")
    public ResponseEntity<Object> addItem(@RequestBody List<CartAddItems.Request> request) {
        cartService.addItem(request);
        return ResponseEntityBuilder.buildOkResponse("성공!");
    }
}
