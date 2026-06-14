package com.bbd.item.adapter.in.kafka;


import com.bbd.item.application.port.in.SyncItemSearchIndexUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 아이템 가격 변경을 발행한 메시지에 대해서 처리하는 Consumer
 */

@Slf4j
@RequiredArgsConstructor
public class ItemEventConsumer {

    private static final String ITEM_PRICE_CHANGED_TOPIC = "item.price.changed";
//    private final SyncItemSearchIndexUseCase syncItemSearchIndexUseCase;

//    @KafkaListener(
//            topics = ITEM_PRICE_CHANGED_TOPIC,
//            groupId = "item-search-group"
//    )
    public void consume(ItemPriceChangedEvent  event) {

        // 읽어오는 이벤트 저장
//        syncItemSearchIndexUseCase.syncPriceChanged(event);

    }


}
