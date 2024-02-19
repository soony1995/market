package com.example.market.service;

import com.example.market.domain.Item;
import com.example.market.dto.item.ItemInfo;
import com.example.market.dto.item.ItemRegister;
import com.example.market.exception.ItemException;
import com.example.market.repository.ItemRepository;
import com.example.market.type.ErrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void itemRegister(ItemRegister.Request request) {
        // 오픈 마켓의 형태가 아니기 때문에, 동일한 상품의 이름은 허용하지 않음.
        itemRepository.findByName(request.getItemName())
                .ifPresent(item -> {
                    throw new ItemException(ErrCode.ITEM_ALREADY_EXIST);
                });

        itemRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public ItemInfo.Response itemInfo(long id) {
        // 아이템이 있는 지 확인
        Item findItem = itemRepository.findById(id).orElseThrow(() -> new ItemException(ErrCode.ITEM_NOT_FOUND));

        return ItemInfo.Response.fromEntity(findItem);
    }

    @Transactional(readOnly = true)
    public List<ItemInfo.Response> getAllItems() {
        // 페이지네이션 필요.
        List<Item> items = itemRepository.findAll();

        return items.stream()
                .map(ItemInfo.Response::fromEntity)
                .collect(Collectors.toList());
    }
}
