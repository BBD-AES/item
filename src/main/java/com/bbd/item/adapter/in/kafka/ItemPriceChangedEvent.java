package com.bbd.item.adapter.in.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPriceChangedEvent {

    private String eventId;
    private String source;       // "item"
    private String eventType;    // "ITEM_PRICE_CHANGED"
    private String occurredAt;
    private String sku;
    private int unitPrice;       // 새 기준단가(원)
}