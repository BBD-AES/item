package com.bbd.item.updateTest;

import com.bbd.item.application.port.in.dto.UpdatePriceCommand;
import com.bbd.item.application.service.ItemServiceGetImpl;
import com.bbd.item.application.service.ItemServiceUpdateImpl;
import com.bbd.item.domain.model.item.Item;
import com.bbd.item.global.error.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UpdateTest2 {

    @Autowired
    private ItemServiceUpdateImpl itemServiceUpdateImpl;

    @Autowired
    private ItemServiceGetImpl itemServiceGetImpl;

    @Test
    @DisplayName("가격 원자처리 업데이트 결과 테스트 - 성공한 경우")
    public void 테스트1() throws Exception{

        // given
        UpdatePriceCommand updatePriceCommand = new UpdatePriceCommand("AC-0580313", 68000);

        // when
        itemServiceUpdateImpl.updatePrice(updatePriceCommand);

        // then
        Item item = itemServiceGetImpl.getItem("AC-0580313");

        Assertions.assertEquals(68000, item.getUnitPrice());

    }

    @Test
    @DisplayName("가격 원자처리 업데이트 결과 테스트 - 실패한 경우")
    public void 테스트2() throws Exception{

        // given
        UpdatePriceCommand updatePriceCommand = new UpdatePriceCommand("AC-0580313aaa", 69000);

        // then
        Assertions.assertThrows(ApiException.class, () -> itemServiceUpdateImpl.updatePrice(updatePriceCommand));

    }



}
