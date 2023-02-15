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
    public Cart findCartItems(Cart cart, Long itemId, int itemPrice) throws RuntimeException {
            List<Cart> cartList = cartRepository.findByMemberIdAndItemId(cart.getMember().getId(), itemId);

            for (Cart c : cartList) {
                Cart dupCartItem = cartRepository.findById(c.getId()).orElseThrow();
                if (cart.getSize() == null && c.getSize() == null && cart.getColor() == null && c.getColor() == null) {
                    dupCartItem.addQuantity(cart.getQuantity());
                    dupCartItem.updatePrice(itemPrice, cart.getQuantity());
                    return dupCartItem;
                }
                if (cart.getSize() == null && c.getColor().equals(cart.getColor())) {
                    dupCartItem.addQuantity(cart.getQuantity());
                    dupCartItem.updatePrice(itemPrice, cart.getQuantity());
                    return dupCartItem;
                }
                if (cart.getColor() == null && c.getSize().equals(cart.getSize())) {
                    System.out.println();
                    dupCartItem.addQuantity(cart.getQuantity());
                    dupCartItem.updatePrice(itemPrice, cart.getQuantity());
                    return dupCartItem;
                }
                if (c.getSize().equals(cart.getSize()) && c.getColor().equals(cart.getColor())) {
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
