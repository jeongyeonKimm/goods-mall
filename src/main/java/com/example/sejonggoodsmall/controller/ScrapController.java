package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.ScrapDTO;
import com.example.sejonggoodsmall.dto.ScrapItemDTO;
import com.example.sejonggoodsmall.model.Scrap;
import com.example.sejonggoodsmall.service.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/scrap")
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String scrapItem(@AuthenticationPrincipal Long memberId,
                                    @PathVariable("itemId") Long itemId) {
       try {
           ScrapDTO scrapDTO = new ScrapDTO(memberId, itemId);

           Scrap scrap = scrapService.insert(scrapDTO);

           String result = "상품을 찜했습니다.";

           return result;
       } catch (Exception e) {
           String error = e.getMessage();
           return error;
       }
    }

    @DeleteMapping(value = "/delete/{itemId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteScrapItem(@AuthenticationPrincipal Long memberId,
                                             @PathVariable("itemId") Long itemId) {
        try {
            ScrapDTO scrapDTO = new ScrapDTO(memberId, itemId);
            scrapService.delete(scrapDTO);

            String result = "찜하기가 취소되었습니다.";
            return result;
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getScrapList(@AuthenticationPrincipal Long memberId) {
        try {
            List<ScrapItemDTO> scrapItemDTOList = scrapService.getScrapList(memberId);

            return ResponseEntity
                    .ok()
                    .body(scrapItemDTOList);
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity
                    .badRequest()
                    .body(error);
        }
    }
}
