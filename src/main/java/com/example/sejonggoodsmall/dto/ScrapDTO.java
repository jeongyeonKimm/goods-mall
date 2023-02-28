package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.Scrap;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Getter
@NoArgsConstructor
public class ScrapDTO {

    private Long memberId;
    private Long itemId;

    public ScrapDTO(Long memberId, Long itemId) {
        this.memberId = memberId;
        this.itemId = itemId;
    }

    private static ModelMapper modelMapper = new ModelMapper();

    public static ScrapDTO of(Scrap scrap){
        return modelMapper.map(scrap, ScrapDTO.class);
    }
}
