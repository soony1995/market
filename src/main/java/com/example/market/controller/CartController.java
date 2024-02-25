package com.example.market.controller;

import com.example.market.domain.Cart;
import com.example.market.dto.cart.CartAddItems;
import com.example.market.dto.cart.CartPatchItems;
import com.example.market.dto.cart.CartQueryItems;
import com.example.market.service.CartService;
import com.example.market.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{version}")
public class CartController {
    private final CartService cartService;

    @GetMapping("/carts")
    public ResponseEntity<List<CartQueryItems.Response>> getCartItems(@PathVariable String version) {
        List<CartQueryItems.Response> response = cartService.getCartItems();
        return ResponseBuilder.buildOkResponse(response);
    }

    @PostMapping("/carts")
    public ResponseEntity<String> addCartItems(@RequestBody List<CartAddItems.Request> request, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(cartService.addCartItems(request));
    }

    @PatchMapping("/carts")
    public ResponseEntity<String> patchCartItems(@RequestBody List<CartPatchItems.Request> request) {
        return ResponseBuilder.buildOkResponse(cartService.patchCartItems(request));
    }

    @PostMapping("/carts/items/delete")
    public ResponseEntity<String> deleteCartItems(@RequestBody List<Long> ids, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(cartService.deleteCartItems(ids));
    }
}
