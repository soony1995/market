package com.example.market.controller;

import com.example.market.dto.cart.CartAddItems;
import com.example.market.dto.cart.CartQueryItems;
import com.example.market.service.CartService;
import com.example.market.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{version}")
public class CartController {
    private final CartService cartService;

    @GetMapping("/carts")
    public ResponseEntity<Object> getItemsFromCart(@PathVariable String version) {
        List<CartQueryItems.Response> response = cartService.getItemsFromCart();
        return ResponseBuilder.buildOkResponse(response);
    }

    @PostMapping("/carts")
    public ResponseEntity<Object> addItemsToCart(@RequestBody @Validated List<CartAddItems.Request> request, @PathVariable String version) {
        cartService.addItemsToCart(request);
        return ResponseBuilder.buildOkResponse("성공!");
    }

    @DeleteMapping("/carts/")
    public ResponseEntity<Object> deleteItemsFromCart(@PathVariable String version) {
        cartService.deleteItemsFromCart();
        return ResponseBuilder.buildOkResponse("성공!");
    }
}
