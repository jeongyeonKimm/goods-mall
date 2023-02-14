package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.CartDTO;
import com.example.sejonggoodsmall.dto.ItemImageDTO;
import com.example.sejonggoodsmall.dto.ResponseDTO;
import com.example.sejonggoodsmall.model.Cart;
import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.service.CartService;
import com.example.sejonggoodsmall.service.ItemService;
import com.example.sejonggoodsmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;
    private final ItemService itemService;

    @PostMapping("/{itemId}")
    public ResponseEntity<?> registerItemInCart(
            @AuthenticationPrincipal Long memberId,
            @PathVariable("itemId") Long itemId,
            @RequestBody CartDTO cartDTO) {

        try {
            Member member = memberService.findById(memberId);
            Item item = itemService.findOne(itemId);

            Cart cart = Cart.toEntity(cartDTO);
            cart.updateMember(member);
            cart.updateItem(item);
            cart.updatePrice(item.getPrice(), cartDTO.getQuantity());

            Cart registeredCartItem = cartService.findCartItems(cart, item.getPrice());

            if (registeredCartItem != null) {
                CartDTO responseCartDTO = CartDTO.of(registeredCartItem);

                return ResponseEntity
                        .ok()
                        .body(responseCartDTO);
            }
            else {
                Cart registeredCart = cartService.register(cart);
                CartDTO responseCartDTO = CartDTO.of(registeredCart);

                return ResponseEntity
                        .ok()
                        .body(responseCartDTO);
            }
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<CartDTO> response = ResponseDTO.<CartDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getCartList(
            @AuthenticationPrincipal Long memberId) {
        List<Cart> cartList = cartService.findCartItemsByMemberId(memberId);

        List<CartDTO> cartDTOList = new ArrayList<>();
        for (Cart cart : cartList) {
            CartDTO cartDTO = CartDTO.of(cart);
            cartDTO.setTitle(cart.getItem().getTitle());
            cartDTO.setRepImage(ItemImageDTO.of(cart.getItem().getItemImages().get(0)));
            cartDTOList.add(cartDTO);
        }

        return ResponseEntity
                .ok()
                .body(cartDTOList);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<?> deleteCartItem(
            @AuthenticationPrincipal Long memberId,
            @PathVariable("cartId") Long cartId) {
        try {
            Cart cartItem = cartService.findOne(cartId);

            if (cartItem.getMember().getId().equals(memberId)) {
                List<Cart> cartList = cartService.delete(cartItem);

                List<CartDTO> cartDTOList = new ArrayList<>();
                for (Cart cart : cartList) {
                    cartDTOList.add(CartDTO.of(cart));
                }

                return ResponseEntity
                        .ok()
                        .body(cartDTOList);
            }
            else {
                log.warn("Unknown member.");
                throw new RuntimeException("Unknown member.");
            }
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<CartDTO> response = ResponseDTO.<CartDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
