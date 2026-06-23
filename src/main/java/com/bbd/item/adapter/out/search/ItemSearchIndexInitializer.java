package com.bbd.item.adapter.out.search;

import com.bbd.item.adapter.out.search.document.ItemSearchDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ItemSearchIndexInitializer {

    private final ElasticsearchOperations elasticsearchOperations;

    @EventListener(ApplicationReadyEvent.class)
    public void initIndex() {
        try {
            IndexOperations indexOps = elasticsearchOperations.indexOps(ItemSearchDocument.class);

            if (indexOps.exists()) {
                log.info("Elasticsearch 인덱스가 이미 존재하여 생성을 건너뜁니다. index=bbd-items");
                return;
            }

            // @Setting(분석기) + @Document/@MultiField(매핑) 기반으로 인덱스 생성
            indexOps.createWithMapping();
            log.info("Elasticsearch 인덱스를 생성했습니다. index=bbd-items");

        } catch (Exception e) {
            log.warn("Elasticsearch 인덱스 초기화에 실패했습니다. 애플리케이션 기동은 계속합니다. index=bbd-items", e);
        }
    }
}
