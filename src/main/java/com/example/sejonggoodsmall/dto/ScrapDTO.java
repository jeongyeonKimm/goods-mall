package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.Scrap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScrapDTO {

    private Long memberId;
    private Long itemId;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ScrapDTO of(Scrap scrap){
        return modelMapper.map(scrap, ScrapDTO.class);
    }
}
