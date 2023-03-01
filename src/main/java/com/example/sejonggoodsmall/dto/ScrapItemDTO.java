package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.ItemImage;
import com.example.sejonggoodsmall.model.Scrap;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Getter
@NoArgsConstructor
public class ScrapItemDTO {

    private Long itemId;
    private String title;
    private String description;
    private int price;
    private ItemImageDTO repImage;

    public ScrapItemDTO(Long itemId, String title, String description, int price, ItemImageDTO repImage) {
        this.itemId = itemId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.repImage = repImage;
    }

    private static ModelMapper modelMapper = new ModelMapper();

    public static ScrapItemDTO of(Scrap scrap){
        return modelMapper.map(scrap, ScrapItemDTO.class);
    }
}
