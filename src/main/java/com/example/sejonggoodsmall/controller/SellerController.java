package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.ResponseDTO;
import com.example.sejonggoodsmall.dto.SellerDTO;
import com.example.sejonggoodsmall.model.Seller;
import com.example.sejonggoodsmall.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SellerDTO sellerDTO) {
        try {
            Seller seller = Seller.builder()
                    .name(sellerDTO.getName())
                    .phoneNumber(sellerDTO.getPhoneNumber())
                    .method(sellerDTO.getMethod())
                    .build();

            Seller registeredSeller = sellerService.join(seller);

            SellerDTO responseDTO = SellerDTO.of(registeredSeller);

            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }
}
