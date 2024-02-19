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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/api/{version}/carts")
    public ResponseEntity<List<CartQueryItems.Response>> getItemsFromCart(@PathVariable String version) {
        return ResponseEntityBuilder.buildOkResponse(cartService.getItemsFromCart());
    }

    @PostMapping("/api/carts")
    public ResponseEntity<String> addItemsToCart(@RequestBody @Validated List<CartAddItems.Request> request) {
        return ResponseEntityBuilder.buildOkResponse(cartService.addItemsToCart(request));
    }

    @DeleteMapping("/api/carts/")
    public ResponseEntity<Boolean> deleteItemsFromCart() {
        cartService.deleteItemsFromCart();
        return ResponseEntityBuilder.buildOkResponse("성공!");
    }
}
