package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.ItemDTO;
import com.example.sejonggoodsmall.dto.ResponseDTO;
import com.example.sejonggoodsmall.model.Category;
import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.ItemImage;
import com.example.sejonggoodsmall.service.CategoryService;
import com.example.sejonggoodsmall.service.ItemImageService;
import com.example.sejonggoodsmall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final CategoryService categoryService;

    /**
     * 상품 등록
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerItem(
            @RequestPart(value = "itemDTO") ItemDTO itemDTO,
            @RequestPart(value = "itemImgList") List<MultipartFile> itemImgList) {

        if(itemImgList.get(0).isEmpty() && itemDTO.getId() == null){
            throw new RuntimeException("대표 이미지는 필수 입력값 입니다.");
        }

        try {
            Item item = ItemDTO.toEntity(itemDTO);
            Category category = categoryService.findOne(itemDTO.getCategoryId()).get();
            item.setCategory(category);

            Item registerItem = itemService.register(item, itemImgList);
            List<ItemImage> registerImage = new ArrayList<>();

            for(int i=0;i<itemImgList.size();i++){
                ItemImage itemImg = new ItemImage();
                itemImg.setItem(registerItem);

                if(i == 0)
                    itemImg.setRepImgUrl("Y");
                else
                    itemImg.setRepImgUrl("N");

                registerImage.add(itemImg);
                itemImageService.saveItemImg(itemImg, itemImgList.get(i));
            }
            registerItem.setItemImages(registerImage);

            ItemDTO responseItemDTO = ItemDTO.of(registerItem);

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
                .color(item.getColor())
                .size(item.getSize())
                .build();

        return ResponseEntity
                .ok()
                .body(itemDTO);
    }
}
