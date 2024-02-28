package com.example.market.domain;

import com.example.market.dto.cart.CartAddItemsDto;
import com.example.market.dto.cart.CartQueryItemsDto;
import com.example.market.dto.cart.CartRemoveItemsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private long id;

    // ManyToOne의 fetch tpye의 기본값이 eager이다
    // cascade = CascadeType.ALL는 부모
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int price;
    private int count;

    public CartAddItemsDto.Response convertToCartAddItemsDto() {
        return CartAddItemsDto.Response.builder()
                .itemName(this.item.getName())
                .price(this.price)
                .count(this.count)
                .build();
    }

    public CartQueryItemsDto.Response convertToCartQueryItemDto() {
        return CartQueryItemsDto.Response.builder()
                .cartId(this.cart.getId())
                .name(this.item.getName())
                .price(this.price)
                .count(this.count)
                .build();
    }

    public CartRemoveItemsDto.Response convertToCartRemoveItemsDto() {
        return CartRemoveItemsDto.Response.builder()
                .itemName(this.item.getName())
                .price(this.price)
                .count(this.count)
                .build();
    }

    public void increaseCount(int count, int price) {
        this.count += count;
        this.price += price;
    }
}
