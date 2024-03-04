package com.example.market.repository;

import com.example.market.domain.CartItem;

import java.util.List;

public interface CartItemCustomRepository {
    long updateItemCount(long cartId, long itemId, int count);

    List<CartItem> queryCartItems(long cartId, List<Long> itemIds);
}
