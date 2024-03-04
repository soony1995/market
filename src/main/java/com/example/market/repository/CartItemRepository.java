package com.example.market.repository;

import com.example.market.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long>, CartItemCustomRepository {
    Optional<CartItem> findByItemIdAndCartId(Long itemId, Long cartId);

    Optional<List<CartItem>> findByCartId(Long cartId);
}
