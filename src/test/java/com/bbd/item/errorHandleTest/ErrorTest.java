package com.bbd.item.errorHandleTest;

import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ErrorTest {

    @Test
    @DisplayName("ApiException 테스트")
    public void test1() {

        Assertions.assertThatThrownBy(() -> {
                    throw new ApiException(ErrorCode.ITEM_NOT_FOUNT);
                })
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("상품을 찾을 수 없습니다.");

    }


}
