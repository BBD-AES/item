package com.bbd.item.creatTest;

import com.bbd.item.adapter.in.web.dto.ItemAutocompleteResponse;
import com.bbd.item.application.port.in.ItemSearchUseCase;
import com.bbd.item.application.port.in.ItemUseCaseCreate;
import com.bbd.item.application.port.in.dto.CreateItemCommand;
import com.bbd.item.domain.model.item.Category;
import com.bbd.item.domain.model.item.SourcingType;
import com.bbd.item.domain.model.item.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CreateTest2 {

    @Autowired
    private ItemUseCaseCreate itemUseCaseCreate;

    @Autowired
    private ItemSearchUseCase itemSearchUseCase;


    @Test
    @DisplayName("상품 생성 시 ES 생성 동기화")
    public void 테스트1() throws Exception {

        // given
        String sku = "TEST-" + System.currentTimeMillis();
        String name = "테스트 상품품 " + System.currentTimeMillis();

        CreateItemCommand item = new CreateItemCommand(
                sku,
                name,
                Category.ACCESSORY_ETC,
                Unit.EA,
                100,
                30000,
                true,
                SourcingType.BUY
        );

        // when
        itemUseCaseCreate.create(item);

        // @Async + AFTER_COMMIT 리스너가 ES에 저장할 시간을 잠깐 기다림
        Thread.sleep(1000);

        // then
        List<ItemAutocompleteResponse> result = itemSearchUseCase.autocomplete("테스트", 5, true,  SourcingType.BUY);

        Assertions.assertTrue(
                result.stream().anyMatch(response -> response.getSku().equals(sku))
        );


    }


}
