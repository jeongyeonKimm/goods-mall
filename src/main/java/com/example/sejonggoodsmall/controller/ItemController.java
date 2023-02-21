package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.CartDTO;
import com.example.sejonggoodsmall.dto.ItemDTO;
import com.example.sejonggoodsmall.dto.ResponseDTO;
import com.example.sejonggoodsmall.model.*;
import com.example.sejonggoodsmall.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final ItemInfoService itemInfoService;
    private final CategoryService categoryService;
    private final CartService cartService;


    /**
     * 상품 등록
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerItem(
            @RequestPart(value = "itemDTO") ItemDTO itemDTO,
            @RequestPart(value = "itemImgList") List<MultipartFile> itemImgList,
            @RequestPart(value = "itemInfoList") List<MultipartFile> itemInfoList) {

        if(itemImgList.get(0).isEmpty() && itemDTO.getId() == null){
            throw new RuntimeException("대표 이미지는 필수 입력값 입니다.");
        }

        try {
            Item item = ItemDTO.toEntity(itemDTO);
            Category category = categoryService.findOne(itemDTO.getCategoryId()).get();
            item.setCategory(category);

            Item registerItem = itemService.register(item, itemImgList);
            List<ItemImage> registerImage = new ArrayList<>();
            List<ItemInfo> registerInfo = new ArrayList<>();

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

            for(int i=0;i<itemInfoList.size();i++){
                ItemInfo itemInfo = new ItemInfo();
                itemInfo.setItem(registerItem);
                registerInfo.add(itemInfo);
                itemInfoService.saveItemInfo(itemInfo, itemInfoList.get(i));
            }
            registerItem.setItemInfos(registerInfo);

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
    public ResponseEntity<?> getAllItems(@RequestBody CartDTO cartDTO) {

        int cartItemCount = cartService.findCartItemsByMemberId(cartDTO.getMemberId()).size();

        System.out.println(cartItemCount);

        List<Item> items = itemService.findAllItems();

        List<ItemDTO> responseItemDTOs = new ArrayList<>();
        for (Item item : items) {
            ItemDTO itemDTO = ItemDTO.of(item);
            itemDTO.setCategoryName(item.getCategory().getName());
            itemDTO.setCartItemCount(cartItemCount);
            responseItemDTOs.add(itemDTO);
        }

        return ResponseEntity
                .ok()
                .body(responseItemDTOs);
    }

    /**
     * 카테고리별 상품 조회
     */
    @GetMapping
    public ResponseEntity<?> getItemsByCategory(@RequestParam(value = "categoryId") Long categoryId) {
        List<Item> items = itemService.findByCategory(categoryId);

        List<ItemDTO> responseItemDTOs = new ArrayList<>();
        for (Item item : items) {
            responseItemDTOs.add(ItemDTO.of(item));
        }

        return ResponseEntity
                .ok()
                .body(responseItemDTOs);
    }

    /**
     * 상품 상세보기
     */
    @GetMapping("/detail/{itemId}")
    public ResponseEntity<?> getItemDetail(@PathVariable("itemId") Long itemId) {
        Item item = itemService.findOne(itemId);

        ItemDTO itemDTO = ItemDTO.of(item);

        return ResponseEntity
                .ok()
                .body(itemDTO);
    }
}
