package com.example.sejonggoodsmall.model;

import com.example.sejonggoodsmall.dto.ItemDTO;
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

    @Column(length = 30)
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

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemOption> itemOptions = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> itemImages = new ArrayList<>();

    // == 연관관계 메서드 == //
    public void setCategory(Category category) {
        this.category = category;
        category.getItems().add(this);
    }

}
