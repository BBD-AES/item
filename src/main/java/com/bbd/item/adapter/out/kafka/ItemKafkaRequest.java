package com.bbd.item.adapter.out.kafka;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemKafkaRequest {

    private String eventId;
    private String source;      // "item" 고정
    private String eventType;    // "ITEM_PRICE_CHANGED" 고정
    private String occurredAt;
    private String sku;
    private int unitPrice;        // 새 기준단가(원)

}
