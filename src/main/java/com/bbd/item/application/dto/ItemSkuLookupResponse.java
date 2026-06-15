package com.bbd.item.application.dto;

import com.bbd.item.domain.model.item.Category;
import com.bbd.item.domain.model.item.Item;
import com.bbd.item.domain.model.item.SourcingType;
import com.bbd.item.domain.model.item.Unit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemSkuLookupResponse {

    private boolean found;
    private String sku;
    private String name;
    private Boolean active;
    private Integer safetyStock;
    private Unit unit;
    private Category category;
    private Integer unitPrice;
    private SourcingType sourcingType;

    public static ItemSkuLookupResponse found(Item item) {
        return new ItemSkuLookupResponse(
                true,
                item.getSku(),
                item.getName(),
                item.isActive(),
                item.getSafetyStock(),
                item.getUnit(),
                item.getCategory(),
                item.getUnitPrice(),
                item.getSourcingType()
        );
    }

    public static ItemSkuLookupResponse notFound(String sku) {
        return new ItemSkuLookupResponse(
                false,
                sku,
                null,
                false,
                null,
                null,
                null,
                null,
                null
        );
    }
}