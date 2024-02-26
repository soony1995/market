package com.example.market.controller;

import com.example.market.dto.item.ItemInfoDto;
import com.example.market.dto.item.ItemRegisterDto;
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
    public ResponseEntity<Object> itemDetails(@PathVariable long id, @PathVariable String version) {
        ItemInfoDto.Response response = itemService.findItem(id);
        return ResponseBuilder.buildOkResponse(response);
    }

    @GetMapping("/items")
    public ResponseEntity<Object> itemList(@PathVariable String version) {
        List<ItemInfoDto.Response> response = itemService.findAllItems();
        return ResponseBuilder.buildOkResponse(response);
    }

    @PostMapping("/items")
    public ResponseEntity<String> itemAdd(@RequestBody ItemRegisterDto.Request request, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(itemService.addItem(request));
    }
}
