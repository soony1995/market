package com.example.market.repository;

import com.example.market.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    //TODO : pagenation
    Page<Item> findAll(Pageable pageable);

    Optional<Item> findByName(String name);
}
