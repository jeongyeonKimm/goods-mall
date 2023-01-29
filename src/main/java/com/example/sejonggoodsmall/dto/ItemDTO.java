package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.ItemStatus;
import com.example.sejonggoodsmall.model.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;

    @NotBlank(message = "상품명을 입력해주세요.")
    private String title;

    @NotBlank(message = "가격을 입력해주세요.")
    private int price;

    //@NotBlank(message = "대표사진을 입력해주세요.")
    //private UploadFile thumbnail;

    private String description;

    public ItemDTO(Item item) {
        this.title = item.getTitle();
        this.price = item.getPrice();
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
                .status(ItemStatus.ACTIVE)
                .build();
    }
}
