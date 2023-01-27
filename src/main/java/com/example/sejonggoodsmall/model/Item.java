package com.example.sejonggoodsmall.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(nullable = false, length = 30)
    private String seller;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false)
    private int price;

    @Embedded
    private UploadFile thumbnail;

    @Lob
    private String description;

    private int viewCount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    private List<ItemOption> itemOptions = new ArrayList<>();

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    private List<ItemImage> itemImages = new ArrayList<>();

    public Item(String seller, String title, int price, String description, ItemStatus status) {
        this.seller = seller;
        this.title = title;
        this.price = price;
        this.description = description;
        this.status = status;
    }
}
