package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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

    private String categoryName;

    @NotBlank(message = "상품명을 입력해주세요.")
    private String title;

    private String color;

    private String size;

    @NotBlank(message = "가격을 입력해주세요.")
    private int price;

    private Seller seller;

    @NotBlank(message = "사진은 한장 이상 입력해주세요.")
    private List<ItemImageDTO> itemImages = new ArrayList<>();

    @NotBlank(message = "상품 상세 사진은 한장 이상 입력해주세요.")
    private List<ItemInfoDTO> itemInfos = new ArrayList<>();

    private String description;
    private int scrapCount;
    private int cartItemCount;
    private boolean isScraped;
    private int deliveryFee;

    private static ModelMapper modelMapper = new ModelMapper();

    public ItemDTO(Item item) {
        this.title = item.getTitle();
        this.price = item.getPrice();
        this.color = item.getColor();
        this.size = item.getSize();
        this.description = item.getDescription();
    }

    public static Item toEntity(final ItemDTO itemDTO) {
        return Item.builder()
                .id(itemDTO.getId())
                .title(itemDTO.getTitle())
                .price(itemDTO.getPrice())
                .description(itemDTO.getDescription())
                .color(itemDTO.getColor())
                .size(itemDTO.getSize())
                .seller(itemDTO.getSeller())
                .status(ItemStatus.ACTIVE)
                .scrapCount(0)
                .isScraped(false)
                .build();

    }

    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemDTO of(Item item){
        return modelMapper.map(item,ItemDTO.class);
    }
}
