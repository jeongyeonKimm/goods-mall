package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.ItemDTO;
import com.example.sejonggoodsmall.dto.ResponseDTO;
import com.example.sejonggoodsmall.model.Category;
import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.ItemImage;
import com.example.sejonggoodsmall.model.ItemOption;
import com.example.sejonggoodsmall.service.CategoryService;
import com.example.sejonggoodsmall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    /**
     * 상품 등록
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerItem(@RequestBody ItemDTO itemDTO) {
        try {
            Item item = ItemDTO.toEntity(itemDTO);
            Category category = categoryService.findOne(itemDTO.getCategoryId()).get();
            item.setCategory(category);

            Item registerItem = itemService.register(item);

            ItemDTO responseItemDTO = ItemDTO.builder()
                    .categoryId(registerItem.getCategory().getId())
                    .id(registerItem.getId())
                    .title(registerItem.getTitle())
                    .price(registerItem.getPrice())
                    .description(registerItem.getDescription())
                    .size(registerItem.getSize())
                    .color(registerItem.getColor())
                    .build();

            return ResponseEntity
                    .ok()
                    .body(responseItemDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    /**
     * 전체 상품 조회
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllItems() {
        List<ItemDTO> items = itemService.findAllItems().stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(items);
    }

    /**
     * 카테고리별 상품 조회
     */
    @GetMapping
    public ResponseEntity<?> getItemsByCategory(@RequestParam(value = "categoryId") Long categoryId) {
        List<ItemDTO> items = itemService.findByCategory(categoryId).stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(items);
    }

    /**
     * 상품 상세보기
     */
    @GetMapping("/detail/{itemId}")
    public ResponseEntity<?> getItemDetail(@PathVariable("itemId") Long itemId) {
        Item item = itemService.findOne(itemId);

        ItemDTO itemDTO = ItemDTO.builder()
                .id(item.getId())
                .title(item.getTitle())
                .price(item.getPrice())
                .description(item.getDescription())
                .itemImages(item.getItemImages())
                .color(item.getColor())
                .size(item.getSize())
                .build();

        return ResponseEntity
                .ok()
                .body(itemDTO);
    }
}
