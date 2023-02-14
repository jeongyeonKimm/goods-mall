package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    @NotBlank(message = "주문자명을 입력해주세요.")
    private String buyerName;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;

    private Address address;

    private List<OrderItemDTO> orderItems = new ArrayList<>();

    private List<Long> cartIdList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public static OrderDTO of(final Order order){
        return modelMapper.map(order, OrderDTO.class);
    }

}
