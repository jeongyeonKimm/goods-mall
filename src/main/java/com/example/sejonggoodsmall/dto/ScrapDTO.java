package com.example.sejonggoodsmall.dto;

import com.example.sejonggoodsmall.model.Scrap;
import lombok.*;
import org.modelmapper.ModelMapper;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScrapDTO {

    private Long memberId;
    private Long itemId;
    private int scrapCount;

    public ScrapDTO(Long memberId, Long itemId) {
        this.memberId = memberId;
        this.itemId = itemId;
    }

    private static ModelMapper modelMapper = new ModelMapper();

    public static ScrapDTO of(Scrap scrap){
        return modelMapper.map(scrap, ScrapDTO.class);
    }
}
