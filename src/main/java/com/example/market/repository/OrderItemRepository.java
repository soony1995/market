package com.example.market.repository;

import com.example.market.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<List<OrderItem>> findByOrderId(long id);
}
