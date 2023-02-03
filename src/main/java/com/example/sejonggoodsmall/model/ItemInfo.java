package com.example.sejonggoodsmall.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfo {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_INFO_ID")
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String infoName;

    @Column(columnDefinition = "LONGTEXT")
    private String oriInfoName;

    @Column(columnDefinition = "LONGTEXT")
    private String infoUrl;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    public void updateItemInfo(String infoName, String oriInfoName, String infoUrl) {
        this.infoName = infoName;
        this.oriInfoName = oriInfoName;
        this.infoUrl = infoUrl;
    }
}
