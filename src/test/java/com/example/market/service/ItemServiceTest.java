package com.example.market.service;

import com.example.market.domain.Item;
import com.example.market.dto.item.ItemRegisterDto;
import com.example.market.exception.CustomException;
import com.example.market.repository.ItemRepository;
import com.example.market.type.ErrCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.naming.Name;
import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("아이템 추가")
class ItemServiceTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    @Test
    @Transactional
    @DisplayName("add_item_정상")
    void addItem() {
        //given
        ItemRegisterDto.Request dto = ItemRegisterDto.Request.builder()
                .name("핸드폰")
                .price(15000)
                .stock(2)
                .description("핸드폰입니다.")
                .build();

        //when
        ItemRegisterDto.Response res = itemService.addItem(dto);

        //then
        assertThat(res.getName()).isEqualTo(dto.getName());
        assertThat(res.getPrice()).isEqualTo(dto.getPrice());
        assertThat(res.getStock()).isEqualTo(dto.getStock());
        assertThat(res.getDescription()).isEqualTo(dto.getDescription());
    }

    @Test
    @Transactional
    @DisplayName("add_item_중복아이템")
    void additem_중복() {
        //given
        ItemRegisterDto.Request dto = ItemRegisterDto.Request.builder()
                .name("충전기")
                .price(15000)
                .stock(2)
                .description("충전시킬 수 있는 도구입니다.")
                .build();

        //then
        assertThatThrownBy(() -> itemService.addItem(dto)).isInstanceOf(CustomException.class)
                .hasMessageContaining("이미 등록된 상품입니다.");
    }

    @Test
    @DisplayName("find_item_성공")
    @Transactional
    void find_item() {
        //given
        Item targetItem = Item.builder()
                .name("충전기")
                .description("충전 시킬 수 있는 도구")
                .stock(2)
                .price(15000)
                .build();

        itemRepository.save(targetItem);

        //when
        Item item = itemRepository.findById(targetItem.getId()).orElseThrow(()
                -> new CustomException(ErrCode.ITEM_NOT_EXIST));

        //then
        assertThat(item).isEqualTo(targetItem);
    }

    @Test
    @DisplayName("find_item_성공")
    @Transactional
    void find_item_없는상품() {
        assertThatThrownBy(() -> itemService.findItem(99L)).isInstanceOf(CustomException.class)
                .hasMessageContaining("찾는 아이템이 존재하지 않습니다.");
    }
}