package com.bbd.item.enumTest;

import com.bbd.item.domain.model.item.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnumTest {

    @Test
    @DisplayName("Enum -> String 변환 테스트")
    public void test1() throws Exception{

        Category a = Category.ELECTRICAL;

        Assertions.assertEquals("전장", a.getLabel());

    }



}
