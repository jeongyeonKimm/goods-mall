package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.Method;
import com.example.sejonggoodsmall.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {

    private Long id;
    private String name;
    private String phoneNumber;
    private String accountHolder;
    private String bank;
    private String account;
    private Method method;

    private static ModelMapper modelMapper = new ModelMapper();

    public static SellerDTO of(Seller seller){
        return modelMapper.map(seller, SellerDTO.class);
    }
}
