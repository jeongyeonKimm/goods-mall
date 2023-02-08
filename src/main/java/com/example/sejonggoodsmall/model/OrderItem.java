package com.example.sejonggoodsmall.model;

import com.example.sejonggoodsmall.dto.OrderDTO;
import com.example.sejonggoodsmall.dto.OrderItemDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @Column(nullable = false)
    private int orderPrice;

    @Column(nullable = false)
    private int count;

    private String color;
    private String size;

    // == 연관관계 메서드 == //
    public void addOrder(Order order) {
        this.order = order;
    }

    // == 생성 메서드 == //
    public static List<OrderItem> createOrderItem(Item item, OrderDTO orderDTO) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO oi : orderDTO.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setOrderPrice(oi.getPrice() * oi.getQuantity());
            orderItem.setCount(oi.getQuantity());
            orderItem.setColor(oi.getColor());
            orderItem.setSize(oi.getSize());
            orderItems.add(orderItem);
        }

        return orderItems;
    }
}
