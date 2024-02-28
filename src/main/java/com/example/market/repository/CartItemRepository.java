package com.example.market.repository;

import com.example.market.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByItemIdAndCartId(Long itemId, Long cartId);

    // TODO: QueryDsl로 변경하는 작업
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.item.id IN :ids")
    Optional<List<CartItem>> findAllByCartIdAndItemIds(@Param("cartId") Long cartId, @Param("ids") List<Long> ids);

    Optional<List<CartItem>> findByCartId(Long cartId);
}
