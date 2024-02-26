package com.example.market.service;

import com.example.market.domain.Item;
import com.example.market.dto.item.ItemInfoDto;
import com.example.market.dto.item.ItemRegisterDto;
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

    // Response 객체 리턴 or dto로 보내던가
    @Transactional
    public Item addItem(ItemRegisterDto.Request request) {
        // 오픈 마켓의 형태가 아니기 때문에, 동일한 상품의 이름은 허용하지 않음.
        // 조회
        // 변경
        // 등록하고
        // 삭제하고

        itemRepository.findByName(request.getName())
                .ifPresent(item -> {
                    throw new ItemException(ErrCode.ITEM_ALREADY_EXIST);
                });

        Item savedItem = itemRepository.save(request.toEntity());
        return savedItem;
    }

    @Transactional(readOnly = true)
    public ItemInfoDto.Response findItem(long id) {
        // 아이템 존재 여부 확인 TODO: 이런건 빼야됩니다.
        Item findItem = itemRepository.findById(id).orElseThrow(() -> new ItemException(ErrCode.ITEM_NOT_FOUND));

        return ItemInfoDto.Response.fromEntity(findItem);
    }

    @Transactional(readOnly = true)
    public List<ItemInfoDto.Response> findAllItems() {
        // TODO: 페이지네이션 필요.
        List<Item> items = itemRepository.findAll();

        return items.stream()
                .map(ItemInfoDto.Response::fromEntity)
                .collect(Collectors.toList());
    }
}
