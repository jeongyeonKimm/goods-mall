package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c " +
            "where c.member.id = :memberId and c.item.id = :itemId and (:size is null or c.size = :size) and (:color is null or c.color = :color) and c.cartMethod = :cartMethod")
    Cart findSameOption(Long memberId, Long itemId, String size, String color, String cartMethod);

    @Query("select c from Cart c where c.member.id = :memberId")
    List<Cart> findByMemberId(@Param("memberId") Long memberId);
}
