package com.example.market.domain;

import com.example.market.dto.item.ItemDetailsDto;
import com.example.market.dto.item.ItemRegisterDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long id;

    private String name;
    private int price;
    private int stock;
    private String description;

    public ItemDetailsDto.Response convertToItemDetailsDto() {
        return ItemDetailsDto.Response.builder()
                .price(this.price)
                .itemName(this.name)
                .stock(this.stock)
                .description(this.description)
                .build();
    }

    public ItemRegisterDto.Response convertToItemRegisterDto() {
        return ItemRegisterDto.Response.builder()
                .name(this.name)
                .price(this.price)
                .stock(this.stock)
                .description(this.description)
                .build();
    }
}
