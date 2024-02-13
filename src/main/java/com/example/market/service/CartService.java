package com.example.market.service;

import com.example.market.dto.cart.CartAddItems;

import java.util.List;

public interface CartService {
    boolean addItem(List<CartAddItems.Request> request);
}
