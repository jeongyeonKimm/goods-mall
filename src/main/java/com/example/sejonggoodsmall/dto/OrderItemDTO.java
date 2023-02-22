package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.Order;
import com.example.sejonggoodsmall.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private String color;
    private String size;
    private int quantity;
    private int price;
    private String seller;

    private static ModelMapper modelMapper = new ModelMapper();

    public static OrderItemDTO of(final OrderItem orderItem){
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

}
