package com.bbd.item.adapter.out.kafka;

import com.bbd.item.application.port.out.ItemEventPublisher;
import com.bbd.item.domain.model.outbox.OutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemEventPublisherAdapter implements ItemEventPublisher {

    private static final String ITEM_PRICE_CHANGED_TOPIC = "item.price.changed";
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(OutboxEvent event) {
        kafkaTemplate.send(ITEM_PRICE_CHANGED_TOPIC, event.getPayload());
    }

}
