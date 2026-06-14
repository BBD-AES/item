package com.bbd.item.adapter.out.search.document;

import com.bbd.item.domain.model.item.Category;
import com.bbd.item.domain.model.item.Item;
import com.bbd.item.domain.model.item.SourcingType;
import com.bbd.item.domain.model.item.Unit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(indexName = "bbd-items", createIndex = false)
public class ItemSearchDocument {

    @Id
    private String sku;

    private String name;

    private boolean active;

    public static ItemSearchDocument from(Item item) {
        return new ItemSearchDocument(
                item.getSku(),
                item.getName(),
                item.isActive()
        );
    }

//    private static int calculateNameSortGroup(String name) {
//        if (name == null || name.isBlank()) {
//            return 3;
//        }
//
//        char first = name.trim().charAt(0);
//
//        // 한글 완성형: 가 ~ 힣
//        if (first >= '가' && first <= '힣') {
//            return 0;
//        }
//
//        // 한글 자음: ㄱ ~ ㅎ
//        if (first >= 'ㄱ' && first <= 'ㅎ') {
//            return 0;
//        }
//
//        // 영문: A-Z, a-z
//        if ((first >= 'A' && first <= 'Z') || (first >= 'a' && first <= 'z')) {
//            return 1;
//        }
//
//        // 숫자
//        if (first >= '0' && first <= '9') {
//            return 2;
//        }
//
//        // 기타 문자
//        return 3;
//    }
}