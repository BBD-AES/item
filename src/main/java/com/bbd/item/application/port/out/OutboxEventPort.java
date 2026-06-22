package com.bbd.item.application.port.out;

import com.bbd.item.domain.model.outbox.OutboxEvent;
import com.bbd.item.domain.model.outbox.OutboxStatus;

import java.util.List;

/**
 * Outbox 패턴 도입을 위해 이벤트를 DB에 우선적으로 저장
 */
public interface OutboxEventPort {

    // 이벤트 저장
    void save(OutboxEvent outBoxEvent);

    // PENDING 상태 아이템 배치 조회
    List<OutboxEvent> getPendingOutboxEvents(int batchSize);



}
