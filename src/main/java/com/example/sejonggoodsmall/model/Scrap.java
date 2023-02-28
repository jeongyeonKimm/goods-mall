package com.example.sejonggoodsmall.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Scrap extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "SCRAP_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;
}
