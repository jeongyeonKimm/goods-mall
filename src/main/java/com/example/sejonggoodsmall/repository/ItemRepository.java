package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

}
