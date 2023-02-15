package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.member.id = :memberId")
    List<Order> findByMember(@Param("memberId") Long memberId);
}
