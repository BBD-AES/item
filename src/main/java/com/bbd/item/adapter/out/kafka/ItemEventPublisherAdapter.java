package com.bbd.item.adapter.out.kafka;

import com.bbd.item.application.port.out.ItemEventPublisher;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemEventPublisherAdapter implements ItemEventPublisher {

    private static final String ITEM_PRICE_CHANGED_TOPIC = "item.changed";
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(OutboxEvent event) {
        try {
            kafkaTemplate
                    .send(ITEM_PRICE_CHANGED_TOPIC, event.getPayload())
                    .get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("카프카 메시지 발행에 대해 실패했습니다. {} 입니다.", e.getMessage(), e);
            throw new ApiException(ErrorCode.ITEM_EVENT_NOT_PUBLISH);
        }
    }

}
