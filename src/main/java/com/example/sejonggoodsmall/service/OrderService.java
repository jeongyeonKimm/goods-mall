package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.dto.OrderDTO;
import com.example.sejonggoodsmall.model.*;
import com.example.sejonggoodsmall.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    @Transactional
    public Order order(Long itemId, OrderDTO orderDTO, Long memberId) {

        Item item = itemRepository.findById(itemId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        Delivery delivery = Delivery.createDelivery(orderDTO);

        List<OrderItem> orderItems = OrderItem.createOrderItem(item, orderDTO);

        Order order = Order.createOrder(member, orderItems, delivery);
        orderRepository.save(order);

        return order;
    }

    @Transactional
    public Order orderInCart(OrderDTO orderDTO, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow();

        Delivery delivery = Delivery.createDelivery(orderDTO);

        List<OrderItem> orderItems = new ArrayList<>();
        for (Long id : orderDTO.getCartIdList()) {
            Cart cart = cartRepository.findById(id).orElseThrow();
            Item item = itemRepository.findById(cart.getItem().getId()).orElseThrow();
            orderItems.add(OrderItem.createOrderItemInCart(item, cart));
            cartRepository.delete(cart);
        }

        Order order = Order.createOrder(member, orderItems, delivery);
        orderRepository.save(order);

        return order;
    }
}
