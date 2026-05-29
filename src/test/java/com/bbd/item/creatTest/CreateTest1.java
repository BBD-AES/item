package com.bbd.item.creatTest;

import com.bbd.item.adapter.out.persistence.ItemJpaRepository;
import com.bbd.item.application.port.in.ItemUseCaseCreate;
import com.bbd.item.application.port.in.ItemUseCaseGet;
import com.bbd.item.application.port.in.ItemUseCaseUpdate;
import com.bbd.item.application.port.in.dto.CreateItemCommand;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.domain.model.Category;
import com.bbd.item.domain.model.Item;
import com.bbd.item.domain.model.Unit;
import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CreateTest1 {

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
    @DisplayName("Item 생성 테스트 - 예외가 없다면 성공")
    public void test1() throws Exception {
        CreateItemCommand createItemCommand = new CreateItemCommand(
                "HQ-Test",
                "엔진",
                Category.ENGINE_OIL,
                Unit.EA,
                5,
                11900,
                true
        );
        Assertions.assertThrows(ApiException.class, () -> {
            itemUseCaseCreate.create(createItemCommand);
        });
        System.out.println("COUNT = " + itemJpaRepository.count());
    }

    @Test
    @DisplayName("Item SKU 로 조회하기")
    public void test2() throws Exception {

        Item item = itemPersistencePort.findBySku("HQ-Test")
                .orElseThrow(() -> new ApiException(ErrorCode.ITEM_CONFLICT));

        assertEquals("HQ-Test", item.getSku());

    }

    @Test
    @DisplayName("UseCase 활용해서 SKU 단건 조회")
    public void test3() throws Exception {

        // given
        String sku = "HQ-Test";

        // when
        Item item = itemUseCaseGet.getItem(sku);

        // then
        assertEquals("HQ-Test", item.getSku());

        System.out.println("item.getSku() = " + item.getSku());
        System.out.println("item.getName() = " + item.getName());
        System.out.println("item.getCategory() = " + item.getCategory());
    }

    @Test
    @DisplayName("상품 존재 여부 테스트")
    public void test4() throws Exception {

        // given
        String sku = "HQ-Test";

        // when
        boolean check = itemPersistencePort.existBySku(sku);

        // then
        assertEquals(true, check);



    }

    @Test
    @DisplayName("이름 변경 테스트")
    public void test5() throws Exception {

        // given
        String sku = "HQ-Test";

        // when
        Item item = itemUseCaseGet.getItem(sku);
        item.changeName("새로운이름");

        // then
        assertEquals("새로운이름", item.getName());

    }


}
