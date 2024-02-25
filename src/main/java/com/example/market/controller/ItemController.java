package com.example.market.controller;

import com.example.market.dto.item.ItemInfo;
import com.example.market.dto.item.ItemRegister;
import com.example.market.service.ItemService;
import com.example.market.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{version}")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/{id}")
    public ResponseEntity<Object> itemInfo(@PathVariable long id, @PathVariable String version) {
        ItemInfo.Response response = itemService.itemInfo(id);
        return ResponseBuilder.buildOkResponse(response);
    }

    @GetMapping("/items")
    public ResponseEntity<Object> getAllItems(@PathVariable String version) {
        List<ItemInfo.Response> response = itemService.getAllItems();
        return ResponseBuilder.buildOkResponse(response);
    }

    @PostMapping("/items")
    public ResponseEntity<Object> itemRegister(@RequestBody ItemRegister.Request request, @PathVariable String version) {
        itemService.itemRegister(request);
        return ResponseBuilder.buildOkResponse();
    }
}
