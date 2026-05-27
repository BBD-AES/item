package com.bbd.item.errorHandleText;

import com.bbd.item.global.error.ApiException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class ErrorTest {

    @Test
    @DisplayName("ApiException 테스트")
    public void test1() {

        Assertions.assertThatThrownBy(() -> {
                    throw new ApiException(HttpStatus.NOT_FOUND, "404", "NOT_FOUND", "Not Found 테스트");
                })
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Not Found 테스트");

    }


}
