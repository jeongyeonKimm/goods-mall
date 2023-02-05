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

    @Transactional
    public Cart findCartItems(Cart cart, int itemPrice) {
        List<Cart> cartList =  cartRepository.findByMemberIdAndItemId(cart.getMember().getId(), cart.getItem().getId());

        for (Cart c : cartList) {
            if (c.getSize().equals(cart.getSize()) && c.getColor().equals(cart.getColor())) {
                Cart dupCartItem = cartRepository.findById(c.getId()).get();
                dupCartItem.addQuantity(cart.getQuantity());
                dupCartItem.updatePrice(itemPrice, cart.getQuantity());
                return dupCartItem;
            }
        }
        return null;
    }

    public List<Cart> findCartItemsByMemberId(Long memberId) {
        return cartRepository.findByMemberId(memberId);
    }
}
