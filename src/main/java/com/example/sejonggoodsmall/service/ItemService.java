package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.dto.ItemDTO;
import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.ItemImage;
import com.example.sejonggoodsmall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item register(Item item, List<MultipartFile> itemImgList) {
        return itemRepository.save(item);
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> findByCategory(Long categoryId) {
        return itemRepository.findByCategory(categoryId);
    }

    public Item findOne(Long itemId) {
        return itemRepository.findItemDetail(itemId);
    }

}
