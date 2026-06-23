package com.bbd.item.application.port.out;

import com.bbd.item.domain.model.outbox.OutboxEvent;

import java.util.List;

public interface OutboxEventPort {

    // 이벤트 저장
    void save(OutboxEvent outBoxEvent);

    // PENDING 상태 아이템 배치 조회
    List<OutboxEvent> getPendingOutboxEvents(int batchSize);



}
