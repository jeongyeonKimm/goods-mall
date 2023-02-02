package com.example.sejonggoodsmall.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ITEM_IMAGES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemImage extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ITEM_IMAGES_ID")
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String imgName; //이미지 파일명

    @Column(columnDefinition = "LONGTEXT")
    private String oriImgName; //원본 이미지 파일명

    @Column(columnDefinition = "LONGTEXT")
    private String imgUrl; //이미지 조회 경로

    @Lob
    private String repImgUrl; //대표 이미지 여부

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;


    public void updateItemImage(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }

}
