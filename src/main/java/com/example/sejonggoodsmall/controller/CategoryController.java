package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.CategoryDTO;
import com.example.sejonggoodsmall.dto.ResponseDTO;
import com.example.sejonggoodsmall.model.Category;
import com.example.sejonggoodsmall.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            Category category = Category.builder()
                    .name(categoryDTO.getName())
                    .build();

            Category registered = categoryService.register(category);

            CategoryDTO responseDTO = CategoryDTO.builder()
                    .id(registered.getId())
                    .name(registered.getName())
                    .build();

            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories().stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(categories);
    }
}
