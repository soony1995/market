package com.example.market.service;

import com.example.market.domain.Item;
import com.example.market.dto.item.ItemDetailsDto;
import com.example.market.dto.item.ItemRegisterDto;
import com.example.market.exception.CustomException;
import com.example.market.repository.ItemRepository;
import com.example.market.type.ErrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public ItemRegisterDto.Response addItem(ItemRegisterDto.Request request) {
        itemRepository.findByName(request.getName())
                .ifPresent(item -> {
                    throw new CustomException(ErrCode.ITEM_ALREADY_EXIST);
                });
        Item item = itemRepository.save(request.toEntity());
        return item.convertToItemRegisterDto();
    }

    @Transactional(readOnly = true)
    public ItemDetailsDto.Response findItem(long id) {
        Item item = itemRepository.findById(id).orElseThrow(()
                -> new CustomException(ErrCode.ITEM_NOT_EXIST));
        return item.convertToItemDetailsDto();
    }

    @Transactional(readOnly = true)
    public List<ItemDetailsDto.Response> findAllItems() {
        Page<Item> items = itemRepository.findAll(Pageable.ofSize(10));
        return items.stream()
                .map(Item::convertToItemDetailsDto)
                .collect(Collectors.toList());
    }
}
