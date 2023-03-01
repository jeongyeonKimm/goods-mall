package com.example.sejonggoodsmall.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false)
    private int price;

    private String color;

    private String size;

    @Lob
    private String description;

    private int scrapCount;
    private boolean isScraped;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "SELLER_ID")
    private Seller seller;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> itemImages = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemInfo> itemInfos = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();

    // == 연관관계 메서드 == //
    public void setCategory(Category category) {
        this.category = category;
        category.getItems().add(this);
    }

    public void setItemImages(List<ItemImage> itemImages) {
        this.itemImages = itemImages;
    }

    public void setItemInfos(List<ItemInfo> itemInfos) {
        this.itemInfos = itemInfos;
    }

    public void addScrapCount() {
        this.scrapCount += 1;
    }

    public void subtractScrapCount() {
        this.scrapCount -= 1;
    }

    public void updateIsScraped() {
        if (this.isScraped == true) {
            this.isScraped = false;
        } else {
            this.isScraped = true;
        }
    }
}
