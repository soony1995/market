package com.example.market.controller;

import com.example.market.dto.item.ItemInfo;
import com.example.market.dto.item.ItemRegister;
import com.example.market.service.ItemService;
import com.example.market.utils.ResponseEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/api/items/{id}")
    public ResponseEntity<Object> itemInfo(@PathVariable long id) {
        ItemInfo.Response response = itemService.itemInfo(id);
        return ResponseEntityBuilder.buildOkResponse(response);
    }

    @GetMapping("/api/items")
    public ResponseEntity<Object> getAllItems() {
        List<ItemInfo.Response> response = itemService.getAllItems();
        return ResponseEntityBuilder.buildOkResponse(response);
    }

    @PostMapping("/api/items")
    public ResponseEntity<Object> itemRegister(@RequestBody ItemRegister.Request request) {
        itemService.itemRegister(request);
        return ResponseEntityBuilder.buildOkResponse();
    }
}
