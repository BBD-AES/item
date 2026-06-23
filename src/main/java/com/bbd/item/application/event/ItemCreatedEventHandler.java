package com.bbd.item.application.event;

import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.application.port.out.ItemSearchPort;
import com.bbd.item.domain.model.item.Item;
import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemCreatedEventHandler {

    private final ItemPersistencePort itemPersistencePort;
    private final ItemSearchPort itemSearchPort;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void elasticSearchItemCreate(ItemCreatedEvent event) {
        try {
            Item item = itemPersistencePort.findBySku(event.sku())
                    .orElseThrow( () -> new ApiException(ErrorCode.ITEM_CREATED_EVENT_ITEM_NOT_FOUND));
            itemSearchPort.save(item);
            log.info("Elasticsearch 자동완성 색인 저장 완료. sku={}", event.sku());

        } catch (Exception e) {
            log.warn("Elasticsearch 자동완성 색인 저장 실패. sku={}", event.sku(), e);
        }
    }
}