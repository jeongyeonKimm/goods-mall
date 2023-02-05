package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.Cart;
import lombok.Data;
import lombok.Getter;
import org.modelmapper.ModelMapper;

@Data
@Getter
public class CartDTO {

    private Long id;
    private Long memberId;
    private Long itemId;
    private int quantity;
    private String color;
    private String size;

    private static ModelMapper modelMapper = new ModelMapper();


    public static CartDTO of(Cart cart){
        return modelMapper.map(cart, CartDTO.class);
    }
}
