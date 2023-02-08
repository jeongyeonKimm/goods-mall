package com.example.sejonggoodsmall.model;

import com.example.sejonggoodsmall.dto.OrderDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Column(name = "ADDRESS_NAME", nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    // == 연관관계 메서드 == //

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    // == 생성 메서드 == //
    public static Delivery createDelivery(OrderDTO orderDTO) {
        Delivery delivery = new Delivery();
        delivery.setAddress(orderDTO.getAddress());
        delivery.setName(orderDTO.getBuyerName());
        delivery.setPhoneNumber(orderDTO.getPhoneNumber());
        delivery.status = DeliveryStatus.READY;

        return delivery;
    }
}
