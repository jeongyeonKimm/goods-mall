package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.ScrapDTO;
import com.example.sejonggoodsmall.model.Scrap;
import com.example.sejonggoodsmall.service.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/scrap")
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping("/{itemId}")
    public ResponseEntity<?> scrapItem(@AuthenticationPrincipal Long memberId,
                                    @PathVariable("itemId") Long itemId) {
       try {
           ScrapDTO scrapDTO = new ScrapDTO(memberId, itemId);

           Scrap scrap = scrapService.insert(scrapDTO);

           ScrapDTO responseDTO = ScrapDTO.of(scrap);

           return ResponseEntity
                   .ok()
                   .body(responseDTO);
       } catch (Exception e) {
           String error = e.getMessage();
           return ResponseEntity
                   .badRequest()
                   .body(error);
       }
    }
}
