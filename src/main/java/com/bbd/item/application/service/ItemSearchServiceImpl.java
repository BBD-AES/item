package com.bbd.item.application.service;

import com.bbd.item.adapter.in.web.dto.ItemAutocompleteResponse;
import com.bbd.item.application.port.in.ItemSearchUseCase;
import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.application.port.out.ItemBulkReadPort;
import com.bbd.item.application.port.out.ItemSearchPort;
import com.bbd.item.domain.model.item.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSearchServiceImpl implements ItemSearchUseCase {

    private static final int BATCH_SIZE = 1000;

    private final ItemBulkReadPort itemBulkReadPort; // RDB에서 Item 읽는 용도
    private final ItemSearchPort itemSearchPort;     // ES에 saveAll 색인 용도

    @Async
    @Override
    public void bulkCreate() {

        // 시작전 모두 지우고 시작
        itemSearchPort.deleteAll();

        String lastSku = null;  // 처음은 null
        long totalIndexed = 0L; // 총 개수

        while (true) {
            // 1. 아이템 리스트로 모두 가져오기
            List<Item> items = lastSku == null
                    ? itemBulkReadPort.findFirstBatch(BATCH_SIZE)
                    : itemBulkReadPort.findNextBatch(lastSku, BATCH_SIZE);

            if (items.isEmpty()) {
                break;
            }

            // 2. 가져온 items 모두 ES 에 동기화 (최대 BATCH_SIZE개)
            itemSearchPort.saveAll(items);

            // 3. totalIndexed 개수 증가
            totalIndexed += items.size();

            // 4. lastSku 업데이트
            lastSku = items.getLast().getSku();

            // 더 이상 찾을게 없다면,
            if (items.size() < BATCH_SIZE) {
                break;
            }
        }
        log.info("Elasticsearch item 동기화 완료. totalIndexed={}", totalIndexed);

    }

    @Override
    public List<ItemAutocompleteResponse> autocomplete(String keyword, int size) {
        return itemSearchPort.autocomplete(keyword, size);
    }

//    @Override
//    public Page<Item> search(Pageable pageable, GetItemFilterCommand command) {
//        return itemSearchPort.getFilter(pageable, command);
//    }
}
