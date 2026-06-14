package com.bbd.item.application.service;


import com.bbd.item.adapter.in.kafka.ItemPriceChangedEvent;
import com.bbd.item.application.port.in.SyncItemSearchIndexUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncItemSearchIndexUseCaseImpl implements SyncItemSearchIndexUseCase {

    @Override
    public void syncPriceChanged(ItemPriceChangedEvent event) {

        String eventId = event.getEventId();

//      1. Redis SETNX로 eventId 선점(PROCESSING)


//      2. 이미 있으면 중복 이벤트라 skip


//      3. 없으면 PostgreSQL에서 Item 재조회


//      4. Elasticsearch 동기화


//      5. 성공하면 Redis에 DONE 저장, TTL 2일


//      6. 실패하면 Redis 키 삭제 후 예외 throw



    }

}
