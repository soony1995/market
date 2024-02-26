package com.example.market.controller;

import com.example.market.dto.cart.CartAddItemsDto;
import com.example.market.dto.cart.CartDeleteItemsDto;
import com.example.market.dto.cart.CartPatchItemsDto;
import com.example.market.dto.cart.CartQueryItemsDto;
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
    public ResponseEntity<List<CartQueryItemsDto.Response>> cartList(@PathVariable String version) {
        List<CartQueryItemsDto.Response> response = cartService.findCartItems();
        return ResponseBuilder.buildOkResponse(response);
    }

    @PostMapping("/carts")
    public ResponseEntity<String> cartAddItems(@RequestBody List<CartAddItemsDto.Request> request, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(cartService.addCartItems(request));
    }

    @PatchMapping("/carts")
    public ResponseEntity<String> cartModify(@RequestBody List<CartPatchItemsDto.Request> request, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(cartService.modifyCartItems(request));
    }

    @PostMapping("/carts/items/delete")
    public ResponseEntity<String> cartRemove(@RequestBody CartDeleteItemsDto.Request request, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(cartService.removeCartItems(request));
    }
}
