package com.example.sejonggoodsmall.controller;

import com.example.sejonggoodsmall.dto.*;
import com.example.sejonggoodsmall.model.Order;
import com.example.sejonggoodsmall.model.OrderItem;
import com.example.sejonggoodsmall.service.MemberService;
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
    private final MemberService memberService;

    @PostMapping("/{itemId}")
    public ResponseEntity<?> orderItem(@AuthenticationPrincipal Long memberId,
                                       @PathVariable("itemId") Long itemId,
                                       @RequestBody OrderDTO orderDTO) {
        try {
            Order order = orderService.order(itemId, orderDTO, memberId);

            List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
            for (OrderItem orderItem : order.getOrderItems()) {
                OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                        .itemId(orderItem.getItem().getId())
                        .price(orderItem.getOrderPrice())
                        .quantity(orderItem.getCount())
                        .size(orderItem.getSize())
                        .color(orderItem.getColor())
                        .deliveryFee(orderItem.getItem().getDeliveryFee())
                        .seller(orderItem.getItem().getSeller())
                        .build();
                orderItemDTOS.add(orderItemDTO);
            }

            OrderDTO responseDTO = OrderDTO.builder()
                    .address(orderDTO.getAddress())
                    .orderItems(orderItemDTOS)
                    .buyerName(orderDTO.getBuyerName())
                    .phoneNumber(orderDTO.getPhoneNumber())
                    .orderMethod(orderDTO.getOrderMethod())
                    .createdAt(order.getCreatedAt())
                    .seller(orderDTO.getSeller())
                    .deliveryRequest(orderDTO.getDeliveryRequest())
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

    @PostMapping("/cart")
    public ResponseEntity<?> orderCartList(@AuthenticationPrincipal Long memberId,
                                           @RequestBody OrderDTO orderDTO) {
        try {
            List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

            Order order = orderService.orderInCart(orderDTO, memberId);

            for (OrderItem orderItem : order.getOrderItems()) {
                OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                        .itemId(orderItem.getItem().getId())
                        .price(orderItem.getOrderPrice())
                        .quantity(orderItem.getCount())
                        .size(orderItem.getSize())
                        .color(orderItem.getColor())
                        .deliveryFee(orderItem.getItem().getDeliveryFee())
                        .seller(orderItem.getItem().getSeller())
                        .build();
                orderItemDTOS.add(orderItemDTO);
            }

            OrderDTO responseDTO = OrderDTO.builder()
                    .address(orderDTO.getAddress())
                    .orderItems(orderItemDTOS)
                    .buyerName(orderDTO.getBuyerName())
                    .phoneNumber(orderDTO.getPhoneNumber())
                    .orderMethod(orderDTO.getOrderMethod())
                    .createdAt(order.getCreatedAt())
                    .seller(orderDTO.getSeller())
                    .deliveryRequest(orderDTO.getDeliveryRequest())
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

    @GetMapping("/list/all")
    public ResponseEntity<?> getOrderList(@AuthenticationPrincipal Long memberId) {
        try {
            List<Order> orders = orderService.findAll(memberId);

            List<OrderDTO> orderDTOList = new ArrayList<>();
            for (Order order : orders) {
                List<OrderItemDTO> dtos = new ArrayList<>();
                for (OrderItem oi : order.getOrderItems()) {
                    OrderItemDTO orderItemDTO = OrderItemDTO.of(oi);
                    orderItemDTO.setItemId(oi.getItem().getId());
                    orderItemDTO.setSeller(oi.getItem().getSeller());
                    orderItemDTO.setQuantity(oi.getCount());
                    dtos.add(orderItemDTO);
                }

                OrderDTO orderDTO = OrderDTO.builder()
                        .id(order.getId())
                        .buyerName(order.getMember().getName())
                        .phoneNumber(order.getDelivery().getPhoneNumber())
                        .address(order.getDelivery().getAddress())
                        .createdAt(order.getCreatedAt())
                        .orderMethod(order.getOrderMethod())
                        .deliveryRequest(order.getDeliveryRequest())
                        .status(order.getStatus())
                        .orderItems(dtos)
                        .build();
                orderDTOList.add(orderDTO);
            }

            return ResponseEntity
                    .ok()
                    .body(orderDTOList);

        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }
}
