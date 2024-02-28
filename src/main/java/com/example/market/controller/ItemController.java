package com.example.market.controller;

import com.example.market.dto.item.ItemDetailsDto;
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
    public ResponseEntity<ItemDetailsDto.Response> itemDetails(@PathVariable long id, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(itemService.findItem(id));
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemDetailsDto.Response>> itemList(@PathVariable String version) {
        return ResponseBuilder.buildOkResponse(itemService.findAllItems());
    }

    @PostMapping("/items")
    public ResponseEntity<ItemRegisterDto.Response> itemAdd(@RequestBody ItemRegisterDto.Request request, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(itemService.addItem(request));
    }
}
