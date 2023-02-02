package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.model.Item;

import java.util.List;

public interface ItemRepositoryCustom {

    List<Item> findByCategory(Long id);
    Item findItemDetail(Long itemId);
}
