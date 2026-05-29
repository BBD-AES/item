package com.bbd.item.creatTest;

import com.bbd.item.adapter.out.persistence.ItemJpaRepository;
import com.bbd.item.application.port.in.ItemUseCase;
import com.bbd.item.application.port.in.dto.CreateItemCommand;
import com.bbd.item.domain.model.Category;
import com.bbd.item.domain.model.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CreateTest1 {

    @Autowired
    private ItemUseCase itemUseCase;

    @Autowired
    private javax.sql.DataSource dataSource;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Test
    @DisplayName("Item 생성 테스트 - 예외가 없다면 성공")
    public void test1 () throws Exception{
        System.out.println("DB URL = " + dataSource.getConnection().getMetaData().getURL());

        System.out.println("DB USER = " + dataSource.getConnection().getMetaData().getUserName());

        CreateItemCommand createItemCommand = new CreateItemCommand(
                "HQ-IW",
                "엔진",
                Category.ENGINE_OIL,
                Unit.EA,
                5,
                11900,
                true
        );
        itemUseCase.create(createItemCommand);


        System.out.println("COUNT = " + itemJpaRepository.count());
    }


}
