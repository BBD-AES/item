package com.bbd.item.adapter.in.web.dto;

import com.bbd.item.adapter.out.search.document.ItemSearchDocument;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 자동 완성용 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemAutocompleteResponse {

    private String sku;

    private String name;

    public static ItemAutocompleteResponse from(ItemSearchDocument document) {
        return new ItemAutocompleteResponse(
                document.getSku(),
                document.getName()
        );
    }
}