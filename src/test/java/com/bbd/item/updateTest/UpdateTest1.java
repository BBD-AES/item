package com.bbd.item.updateTest;

import com.bbd.item.adapter.out.persistence.ItemJpaRepository;
import com.bbd.item.application.port.in.ItemUseCaseCreate;
import com.bbd.item.application.port.in.ItemUseCaseGet;
import com.bbd.item.application.port.in.ItemUseCaseUpdate;
import com.bbd.item.application.port.in.dto.CreateItemCommand;
import com.bbd.item.application.port.in.dto.UpdateCommand;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.domain.model.Category;
import com.bbd.item.domain.model.Item;
import com.bbd.item.domain.model.Unit;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UpdateTest1 {

    @Autowired
    private EntityManager em;

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
    }

    @Test
    @DisplayName("제품 이름 및 단가 변경 테스트")
    public void test1() throws Exception{

       // given
        UpdateCommand updateCommand = new UpdateCommand("HQ-Test", "엔진v2", 10000);

       // when
        itemUseCaseUpdate.update(updateCommand);
        em.flush(); // 커밋
        em.clear(); // 클리어
        Item item = itemUseCaseGet.getItem("HQ-Test");

        // then
        assertEquals("엔진v2", item.getName());
        assertEquals(10000, item.getUnitPrice());

    }





}
