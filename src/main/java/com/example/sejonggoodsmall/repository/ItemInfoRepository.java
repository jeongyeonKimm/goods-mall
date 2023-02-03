package com.example.sejonggoodsmall.repository;

import com.example.sejonggoodsmall.model.ItemInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInfoRepository extends JpaRepository<ItemInfo, Long> {
}
