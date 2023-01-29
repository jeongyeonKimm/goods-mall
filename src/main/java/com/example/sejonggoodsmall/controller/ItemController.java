package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.ItemDTO;
import com.example.sejonggoodsmall.dto.ResponseDTO;
import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.ItemStatus;
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

    @PostMapping("/register")
    public ResponseEntity<?> registerItem(@RequestBody ItemDTO itemDTO) {
        try {
            Item item = ItemDTO.toEntity(itemDTO);

            Item registerItem = itemService.register(item);

            ItemDTO responseItemDTO = ItemDTO.builder()
                    .id(registerItem.getId())
                    .title(registerItem.getTitle())
                    .price(registerItem.getPrice())
                    .description(registerItem.getDescription())
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
}
