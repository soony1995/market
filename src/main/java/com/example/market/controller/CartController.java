package com.example.market.controller;

import com.example.market.dto.cart.CartAddItems;
import com.example.market.dto.cart.CartQueryItems;
import com.example.market.dto.item.ItemInfo;
import com.example.market.service.CartService;
import com.example.market.utils.ResponseEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/api/carts")
    public ResponseEntity<Object> getItemsFromCart() {
        List<CartQueryItems.Response> response = cartService.getItemsFromCart();
        return ResponseEntityBuilder.buildOkResponse(response);
    }

    @PostMapping("/api/carts")
    public ResponseEntity<Object> addItemsToCart(@RequestBody @Validated List<CartAddItems.Request> request) {
        cartService.addItemsToCart(request);
        return ResponseEntityBuilder.buildOkResponse("성공!");
    }

    @DeleteMapping("/api/carts/")
    public ResponseEntity<Object> deleteItemsFromCart() {
        cartService.deleteItemsFromCart();
        return ResponseEntityBuilder.buildOkResponse("성공!");
    }
}
