package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.Member;
import com.example.sejonggoodsmall.model.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Scrap findByMemberAndItem(Member member, Item item);
    List<Scrap> findByMember(Member member);
}
