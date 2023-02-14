package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.CartDTO;
import com.example.sejonggoodsmall.dto.OrderDTO;
import com.example.sejonggoodsmall.dto.OrderItemDTO;
import com.example.sejonggoodsmall.dto.ResponseDTO;
import com.example.sejonggoodsmall.model.Order;
import com.example.sejonggoodsmall.model.OrderItem;
import com.example.sejonggoodsmall.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{itemId}")
    public ResponseEntity<?> orderItem(@AuthenticationPrincipal Long memberId,
                                       @PathVariable("itemId") Long itemId,
                                       @RequestBody OrderDTO orderDTO) {
        try {
            Order order = orderService.order(itemId, orderDTO, memberId);

            List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
            for (OrderItem orderItem : order.getOrderItems()) {
                OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                        .price(orderItem.getOrderPrice())
                        .quantity(orderItem.getCount())
                        .size(orderItem.getSize())
                        .color(orderItem.getColor())
                        .build();
                orderItemDTOS.add(orderItemDTO);
            }

            OrderDTO responseDTO = OrderDTO.builder()
                    .address(orderDTO.getAddress())
                    .orderItems(orderItemDTOS)
                    .buyerName(orderDTO.getBuyerName())
                    .phoneNumber(orderDTO.getPhoneNumber())
                    .build();

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

//    @PostMapping("/cart")
//    public ResponseEntity<?> orderCartList(@AuthenticationPrincipal Long memberId,
//                                           @RequestBody OrderDTO orderDTO, List<CartDTO> cartDTOList) {
//        try {
//            for (CartDTO cartDTO : cartDTOList) {
//
//            }
//        } catch (Exception e) {
//            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
//            return ResponseEntity
//                    .badRequest()
//                    .body(responseDTO);
//        }
//    }
}
