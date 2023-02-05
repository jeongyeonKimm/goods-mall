package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.dto.CartDTO;
import com.example.sejonggoodsmall.model.Cart;
import com.example.sejonggoodsmall.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    @Transactional
    public Cart register(Cart cart) {
        return cartRepository.save(cart);
    }

    public boolean findCartItems(Cart cart) {
        List<Cart> cartList =  cartRepository.findByMemberIdAndItemId(cart.getMember().getId(), cart.getItem().getId());

        for (Cart c : cartList) {
            if (c.getSize().equals(cart.getSize()) && c.getColor().equals(cart.getColor())) {
                return true;
            }
        }
        return false;
    }

}
