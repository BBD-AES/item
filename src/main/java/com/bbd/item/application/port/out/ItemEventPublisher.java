package com.bbd.item.application.port.out;

import com.bbd.item.domain.model.outbox.OutboxEvent;

public interface ItemEventPublisher {

    void publish(OutboxEvent event);

}
