package com.example.sejonggoodsmall.service;

import com.example.sejonggoodsmall.model.Item;
import com.example.sejonggoodsmall.model.ItemStatus;
import com.example.sejonggoodsmall.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void saveItem() throws Exception {

        // Given
        Item item = new Item("user1", "학잠", 10000, "#학잠", ItemStatus.ACTIVE);

        // When
        Long saveId = itemService.saveItem(item);

        // Then
        assertEquals(item, itemRepository.findOne(saveId));
    }
    
}
