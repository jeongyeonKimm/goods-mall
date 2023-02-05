package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.CartDTO;
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

            boolean isAlreadyExisted = cartService.findCartItems(cart);

            if (isAlreadyExisted) throw new RuntimeException("이미 장바구니에 있습니다.");
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
    
}
