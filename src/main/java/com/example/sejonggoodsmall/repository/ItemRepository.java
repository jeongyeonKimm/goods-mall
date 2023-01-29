package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i where i.status = 'ACTIVE'")
    List<Item> findAllItems();
}
