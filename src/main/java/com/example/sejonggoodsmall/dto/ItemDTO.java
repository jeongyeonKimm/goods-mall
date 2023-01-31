package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;

    @NotBlank(message = "카테고리를 입력해주세요.")
    private Long categoryId;

    @NotBlank(message = "상품명을 입력해주세요.")
    private String title;

    private String color;

    private String size;

    @NotBlank(message = "가격을 입력해주세요.")
    private int price;

    @NotBlank(message = "대표사진을 입력해주세요.")
    private UploadFile thumbnail;

    private List<ItemImage> itemImages = new ArrayList<>();

    private String description;


    public ItemDTO(Item item) {
        this.title = item.getTitle();
        this.price = item.getPrice();
        this.color = item.getColor();
        this.size = item.getSize();
        //this.thumbnail = item.getThumbnail();
        this.description = item.getDescription();
    }

    public static Item toEntity(final ItemDTO itemDTO) {
        return Item.builder()
                .id(itemDTO.getId())
                .title(itemDTO.getTitle())
                .price(itemDTO.getPrice())
                //.thumbnail(itemDTO.getThumbnail())
                .description(itemDTO.getDescription())
                .color(itemDTO.getColor())
                .size(itemDTO.getSize())
                .status(ItemStatus.ACTIVE)
                .build();

    }
}
