package com.bbd.item.getTest;

import com.bbd.item.adapter.in.web.dto.ItemListSku;
import com.bbd.item.application.port.in.ItemUseCaseGet;
import com.bbd.item.domain.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GetTest3 {
    
    @Autowired
    private ItemUseCaseGet itemUseCaseGet;

    @Test
    @DisplayName("SKU 를 리스트로 보냈을 떄 조회 반환")
    public void test1() throws Exception {

        // given
        List<String> list = new ArrayList<>();
        list.add("INT-0736572");
        list.add("WIPER-0736573");
        list.add("ACC-0736574");
        list.add("ENGOIL-0736575");
        list.add("FILTER-0736576");

        ItemListSku itemListSku = new ItemListSku(list);

        // when
        List<Item> allInSku = itemUseCaseGet.getAllInSku(itemListSku);

        // then
        Assertions.assertEquals(5, allInSku.size());
        
        for(Item item:allInSku){
            System.out.println(item.getName());
        }
        

    }


}
