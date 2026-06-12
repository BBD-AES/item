package com.bbd.item.adapter.out.search.document;

import com.bbd.item.domain.model.item.Item;
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

    private String category;

    private String unit;

    private int safetyStock;

    private int unitPrice;

    private boolean active;

    private String sourcingType;

    public static ItemSearchDocument from(Item item) {
        return new ItemSearchDocument(
                item.getSku(),
                item.getName(),
                item.getCategory().name(),
                item.getUnit().name(),
                item.getSafetyStock(),
                item.getUnitPrice(),
                item.isActive(),
                item.getSourcingType().name()
        );
    }

}