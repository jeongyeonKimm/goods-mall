package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.dto.CartDTO;
import com.example.sejonggoodsmall.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.member.id = :memberId and c.item.id = :itemId")
    List<Cart> findByMemberIdAndItemId(@Param("memberId") Long memberId, @Param("itemId") Long itemId);

    @Query("select c from Cart c where c.member.id = :memberId")
    List<Cart> findByMemberId(@Param("memberId") Long memberId);
}
