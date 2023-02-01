package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.ItemImage;
import lombok.*;
import org.modelmapper.ModelMapper;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemImageDTO {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgUrl;

    private static ModelMapper modelMapper = new ModelMapper();


    public static ItemImageDTO of(ItemImage itemImage){
        return modelMapper.map(itemImage, ItemImageDTO.class);
    }
}
