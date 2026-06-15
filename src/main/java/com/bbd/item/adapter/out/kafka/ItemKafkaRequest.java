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
    private ItemEventType eventType;   // ITEM_PRICE_CHANGED, ITEM_ACTIVE_CHANGED
    private String occurredAt;
    private String sku;
    private Integer unitPrice;  // 가격 변경 이벤트에서 사용
    private Boolean active;     // 활성/비활성 이벤트에서 사용

}
