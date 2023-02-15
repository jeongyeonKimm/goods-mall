package com.example.sejonggoodsmall.model;

import com.example.sejonggoodsmall.dto.CartDTO;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "CART_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @Column(nullable = false)
    private int quantity;
    private String size;
    private String color;

    @Column(nullable = false)
    private int price;

    public static Cart toEntity(final CartDTO cartDTO) {
        return Cart.builder()
                .id(cartDTO.getId())
                .quantity(cartDTO.getQuantity())
                .size(cartDTO.getSize())
                .color(cartDTO.getColor())
                .price(0)
                .build();
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateItem(Item item) {
        this.item = item;
    }

    public void addPrice(int price, int quantity) {
        this.price += price * quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updatePrice(int price, int quantity) {
        this.price = price * quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
