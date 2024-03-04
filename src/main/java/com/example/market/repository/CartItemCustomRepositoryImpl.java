package com.example.market.repository;


import com.example.market.domain.CartItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.market.domain.QCartItem.cartItem;
import static com.example.market.domain.QItem.item;

@RequiredArgsConstructor
public class CartItemCustomRepositoryImpl implements CartItemCustomRepository {
    private final JPAQueryFactory query;

    @Override
    public long updateItemCount(long cartId, long itemId, int count) {
        return query
                .update(cartItem)
                .set(cartItem.count, count)
                .where(cartItem.cart.id.eq(cartId),cartItem.item.id.eq(itemId))
                .execute();
    }

    @Override
    public List<CartItem> queryCartItems(long cartId, List<Long> itemIds) {
        return query
                .selectFrom(cartItem)
                .where(cartItem.id.eq(cartId),cartItem.item.id.in(itemIds))
                .fetch();
    }
}
