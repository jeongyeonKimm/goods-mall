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

import static com.example.sejonggoodsmall.model.OrderStatus.ORDER;
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

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // == 연관관계 메서드 == //
    public void addOrder(Order order) {
        this.order = order;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // == 생성 메서드 == //
    public static List<OrderItem> createOrderItem(Item item, OrderDTO orderDTO) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO oi : orderDTO.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setOrderPrice(item.getPrice() * oi.getQuantity());
            orderItem.setCount(oi.getQuantity());
            orderItem.setColor(oi.getColor());
            orderItem.setSize(oi.getSize());
            orderItem.setStatus(ORDER);
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    public static OrderItem createOrderItemInCart(Item item, Cart cart) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(item.getPrice() * cart.getQuantity());
        orderItem.setCount(cart.getQuantity());
        orderItem.setColor(cart.getColor());
        orderItem.setSize(cart.getSize());
        orderItem.setStatus(ORDER);

        return orderItem;
    }

}
