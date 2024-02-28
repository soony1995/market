package com.example.market.controller;


import com.example.market.dto.order.OrderCreateDto;
import com.example.market.dto.order.OrdersDto;
import com.example.market.dto.order.OrderItemsDto;
import com.example.market.dto.order.OrderModifyStatusDto;
import com.example.market.service.OrderService;
import com.example.market.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{version}")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<List<OrderCreateDto.Response>> orderAdd(@RequestBody OrderCreateDto.Request request, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(orderService.addOrder(request));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrdersDto.Response>> findAllOrders(@PathVariable String version) {
        return ResponseBuilder.buildOkResponse(orderService.findAllOrders());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<List<OrderItemsDto.Response>> orderDetails(@PathVariable long id, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(orderService.findOrder(id));
    }

    @PatchMapping("/orders/{id}")
    public ResponseEntity<OrderModifyStatusDto.Response> modifyOrderStatus(@RequestBody OrderModifyStatusDto.Request request, @PathVariable long id, @PathVariable String version) {
        return ResponseBuilder.buildOkResponse(orderService.modifyOrderStatus(id, request));
    }
}
