package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.model.Cart;
import com.example.sejonggoodsmall.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
    public Cart findCartItems(Cart cart, Long itemId, int itemPrice) {
        Cart cartList = cartRepository.findSameOption(cart.getMember().getId(), itemId, cart.getSize(), cart.getColor(), cart.getCartMethod());

        if (cartList != null) {
            Cart dupCartItem = cartRepository.findById(cartList.getId()).orElseThrow();
            dupCartItem.addQuantity(cart.getQuantity());
            dupCartItem.addPrice(itemPrice, cart.getQuantity());
            return dupCartItem;
        } else {
            return null;
        }

    }

    public List<Cart> findCartItemsByMemberId(Long memberId) {
        return cartRepository.findByMemberId(memberId);
    }

    public Cart findOne(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow();
    }

    @Transactional
    public List<Cart> delete(Cart cart) {
        validate(cart);

        try {
            cartRepository.delete(cart);
        } catch (Exception e) {
            log.error("error deleting cart item ", cart.getId(), e);
            throw new RuntimeException("error deleting cart item " + cart.getId());
        }

        return findCartItemsByMemberId(cart.getMember().getId());
    }

    @Transactional
    public Cart updateCartItem(Cart cart, int quantity) {
        try {

            cart.updateQuantity(quantity);
            cart.updatePrice(cart.getItem().getPrice(), quantity);

            return cart;
        } catch (Exception e) {
            log.error("error updating cart item ", cart.getId(), e);
            throw new RuntimeException("error updating cart item " + cart.getId());
        }
    }

    private void validate(final Cart entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if (entity.getMember().getId() == null) {
            log.warn("Unknown member.");
            throw new RuntimeException("Unknown member.");
        }
    }
}
