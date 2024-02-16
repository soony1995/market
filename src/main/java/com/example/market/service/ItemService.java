package com.example.market.service;

import com.example.market.dto.item.ItemRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService{
    public void itemRegister(ItemRegister.Request request) {
        System.out.println("hi");
    }
}
