package com.bbd.item.getTest;

import com.bbd.item.adapter.out.persistence.ItemJpaRepository;
import com.bbd.item.application.port.in.ItemUseCaseCreate;
import com.bbd.item.application.port.in.ItemUseCaseGet;
import com.bbd.item.application.port.in.ItemUseCaseUpdate;
import com.bbd.item.application.port.in.dto.CreateItemCommand;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.domain.model.Category;
import com.bbd.item.domain.model.Item;
import com.bbd.item.domain.model.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class GetTest1 {

    @Autowired
    private ItemUseCaseUpdate itemUseCaseUpdate;

    @Autowired
    private ItemUseCaseGet itemUseCaseGet;

    @Autowired
    private ItemUseCaseCreate itemUseCaseCreate;

    @Autowired
    private javax.sql.DataSource dataSource;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private ItemPersistencePort itemPersistencePort;

    @BeforeEach
    public void prePersist() {
        CreateItemCommand createItemCommand = new CreateItemCommand(
                "HQ-Test",
                "엔진",
                Category.ENGINE_OIL,
                Unit.EA,
                5,
                11900,
                true
        );
        itemUseCaseCreate.create(createItemCommand);
        CreateItemCommand createItemCommand2 = new CreateItemCommand(
                "HQ-Test2",
                "엔진2",
                Category.ENGINE_OIL,
                Unit.EA,
                10,
                21900,
                true
        );
        itemUseCaseCreate.create(createItemCommand2);
    }

    @Test
    @DisplayName("전체 조회 테스트")
    public void test1() throws Exception {
        List<Item> list = itemUseCaseGet.getAll()
                .stream()
                .toList();
        // 기본데이터 2개 총 2개 담겨있어야함
        Assertions.assertEquals(2, list.size());
    }


}
