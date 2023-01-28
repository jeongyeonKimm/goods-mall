package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.ItemDTO;
import com.example.sejonggoodsmall.dto.MemberDTO;
import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.LoginMember;
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

    @GetMapping
    public ResponseEntity<?> getAllItems() {
        List<ItemDTO> items = itemService.findAllItems().stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(items);
    }
}
