package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.model.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.sejonggoodsmall.model.QCategoryItem.categoryItem;
import static com.example.sejonggoodsmall.model.QItem.item;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Item> findByCategory(Long id) {
        return jpaQueryFactory
                .select(item)
                .from(item)
                .join(item.categoryItems, categoryItem)
                .where(categoryItem.category.id.eq(id))
                .fetch();
    }
}
