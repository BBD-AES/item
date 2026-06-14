package com.bbd.item.application.service;


import com.bbd.item.adapter.in.kafka.ItemPriceChangedEvent;
import com.bbd.item.application.port.in.SyncItemSearchIndexUseCase;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.application.port.out.ItemSearchPort;
import com.bbd.item.application.port.out.ProcessedEventPort;
import com.bbd.item.domain.model.item.Item;
import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncItemSearchIndexUseCaseImpl implements SyncItemSearchIndexUseCase {

    private final ProcessedEventPort processedEventPort;
    private final ItemPersistencePort itemPersistencePort;
    private final ItemSearchPort itemSearchPort;

    @Override
    public void syncPriceChanged(ItemPriceChangedEvent event) {

        String eventId = event.getEventId();

//      1. Redis SETNX로 eventId 선점하기
        if (!processedEventPort.tryStart(eventId)) {
            log.info("이미 처리된 이벤트는 SKIP 합니다.");
            return; // 중복이벤트는 넘어가기
        }

        try {
            // 2. 선점 성공 시 PostgreSQL에서 Item 재조회
            Item item = itemPersistencePort.findBySku(event.getSku())
                    .orElseThrow(() -> new ApiException(ErrorCode.ITEM_NOT_FOUND)); // 찾을 수 없는경우

            // 3. Elasticsearch 동기화
            itemSearchPort.save(item);

            // 4. 성공하면 Redis에 DONE 저장, TTL 2일
            processedEventPort.markDone(eventId);

        } catch (ApiException e) {
            // 5. 실패하면 Redis 키 삭제 후 기존 ApiException 유지
            processedEventPort.release(eventId);

            log.error("엘라스틱 서치 동기화중 아이템을 찾을 수 없습니다. eventId={}, sku={}",
                    eventId, event.getSku(), e);
            throw e;

        } catch (Exception e) {
            // 6. 똑같이 Redis 키 삭제
            processedEventPort.release(eventId);
            log.error("엘라스틱 서치 동기화중 알 수 없는 에러로 인해 저장 실패했습니다. eventId={}, sku={}",
                    eventId, event.getSku(), e);


            throw new ApiException(ErrorCode.ITEM_SEARCH_INDEX_SYNC_FAIL);
        }

    }

}
