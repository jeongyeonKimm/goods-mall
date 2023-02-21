package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.model.Seller;
import com.example.sejonggoodsmall.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    @Transactional
    public Seller join(Seller seller) {
        return sellerRepository.save(seller);
    }
}
