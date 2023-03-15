package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.Order;
import com.example.sejonggoodsmall.model.OrderItem;
import com.example.sejonggoodsmall.model.OrderStatus;
import com.example.sejonggoodsmall.model.Seller;
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

    private Long itemId;
    private String color;
    private String size;
    private int quantity;
    private int price;
    private int deliveryFee;
    private Seller seller;
    private OrderStatus orderStatus;

    private static ModelMapper modelMapper = new ModelMapper();

    public static OrderItemDTO of(final OrderItem orderItem){
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
