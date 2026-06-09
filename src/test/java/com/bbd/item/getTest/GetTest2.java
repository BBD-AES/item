package com.bbd.item.getTest;

import com.bbd.item.adapter.out.persistence.ItemJpaEntity;
import com.bbd.item.adapter.out.persistence.ItemJpaRepository;
import com.bbd.item.adapter.out.persistence.ItemPersistenceMapper;
import com.bbd.item.application.port.in.ItemUseCaseGet;
import com.bbd.item.domain.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class GetTest2 {

    @Autowired
    private ItemUseCaseGet itemUseCaseGet;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private ItemPersistenceMapper itemPersistenceMapper;

    @Test
    @DisplayName("Page을 통한 getAll 테스트")
    public void test1() throws Exception{

        // given
        Sort.Direction sortDirection = Sort.Direction.fromString("ASC");
        Pageable pageable = PageRequest.of(0, 20, Sort.by(sortDirection, "name"));

        // when
        Page<Item> all = itemUseCaseGet.getAll(pageable);

        // then
        Assertions.assertEquals(20, all.getContent().size());

        // 체크용도
        System.out.println("all.getNumber() = " + all.getNumber());
        System.out.println("all.getTotalElements() = " + all.getTotalElements());
        System.out.println("all.getTotalPages() = " + all.getTotalPages());
        System.out.println("all.getContent() = " + all.getContent());
        

    }

    @Test
    @DisplayName("nativeQuery테스트")
    public void test2() throws Exception{

        // given
        Sort.Direction sortDirection = Sort.Direction.fromString("ASC");
        Pageable pageable = PageRequest.of(0, 20, Sort.by(sortDirection, "name"));

        // when
        Page<ItemJpaEntity> all = itemJpaRepository.getNative(pageable);
        Page<Item> map = all.map(i -> itemPersistenceMapper.toDomain(i));

        Assertions.assertEquals(20, map.getContent().size());
        for(Item item : map.getContent()){
            System.out.println(item.getSku() +" & "+item.getName());
        }

    }




}
