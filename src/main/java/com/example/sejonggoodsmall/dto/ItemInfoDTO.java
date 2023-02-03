package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.ItemImage;
import com.example.sejonggoodsmall.model.ItemInfo;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class ItemInfoDTO {

    private String infoUrl;

    private static ModelMapper modelMapper = new ModelMapper();


    public static ItemInfoDTO of(ItemInfo itemInfo){
        return modelMapper.map(itemInfo, ItemInfoDTO.class);
    }
}
