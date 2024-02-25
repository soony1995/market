package com.example.market.controller;

import com.example.market.dto.order.OrderInfo;
import com.example.market.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{version}")
public class OrderController {

    @GetMapping("/order/{id}")
    public ResponseEntity<Object> getOrder(@PathVariable long id, @PathVariable String version) {
        OrderInfo.Response  response = OrderService.orderInfo(id);
        return ResponseBuilder.buildOkResponse(response);
    }
}
