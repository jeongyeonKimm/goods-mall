package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.model.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.sejonggoodsmall.model.QCategory.category;
import static com.example.sejonggoodsmall.model.QItem.item;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Item> findByCategory(Long categoryId) {
        return jpaQueryFactory
                .select(item)
                .from(item)
                .join(item.category, category).fetchJoin()
                .where(item.category.id.eq(categoryId))
                .fetch();
    }

    @Override
    public Item findItemDetail(Long itemId) {
        return jpaQueryFactory
                .select(item)
                .from(item)
                .where(item.id.eq(itemId))
                .fetchOne();
    }
}
