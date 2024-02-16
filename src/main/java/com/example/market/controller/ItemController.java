package com.example.market.controller;

import com.example.market.dto.item.ItemRegister;
import com.example.market.service.ItemService;
import com.example.market.utils.ResponseEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @PostMapping("/api/items")
    public ResponseEntity<Object> itemRegister(@RequestBody ItemRegister.Request request) {
        itemService.itemRegister(request);
        return ResponseEntityBuilder.buildOkResponse();
    }
}
