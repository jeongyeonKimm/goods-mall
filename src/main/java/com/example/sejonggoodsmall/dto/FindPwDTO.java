package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindPwDTO {

    private String name;
    private String email;
    private int authNumber;

    private static ModelMapper modelMapper = new ModelMapper();

    public static FindPwDTO of(final Member member){
        return modelMapper.map(member, FindPwDTO.class);
    }
}
