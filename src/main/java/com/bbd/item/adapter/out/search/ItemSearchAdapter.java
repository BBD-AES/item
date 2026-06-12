package com.bbd.item.adapter.out.search;

import com.bbd.item.adapter.in.web.dto.ItemAutocompleteResponse;
import com.bbd.item.adapter.out.search.document.ItemSearchDocument;
import com.bbd.item.adapter.out.search.repository.ItemSearchRepository;
import com.bbd.item.application.port.out.ItemSearchPort;
import com.bbd.item.domain.model.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemSearchAdapter implements ItemSearchPort {

    private final ItemSearchRepository itemSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    // 전체 삭제 (초기화)
    @Override
    public void deleteAll() {
        itemSearchRepository.deleteAll();
    }

    // 단건 저장 (수정은 다시 save하면 됨)
    @Override
    public void save(Item item){
        itemSearchRepository.save(ItemSearchDocument.from(item));
    }

    // 여러 건 저장
    @Override
    public void saveAll(List<Item> items){
        List<ItemSearchDocument> list = items.stream()
                .map(item -> ItemSearchDocument.from(item))
                .toList();
        itemSearchRepository.saveAll(list);
    }

    @Override
    public List<ItemAutocompleteResponse> autocomplete(String keyword, int size) {
        // 엘라스틱서치에서 읽을 수 있는 쿼리 만들기
        // name 기준으로 autocomplete 필드를 사용하고 keywork 로 탐색
        // active 즉, 사용 가능한것만 검색
        // size로 인해 너무 많이 말고 정해진 크기만 조회
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.bool(b -> b
                        .must(m -> m.match(mm -> mm
                                .field("name.autocomplete")
                                .query(keyword)
                        ))
                        .filter(f -> f.term(t -> t
                                .field("active")
                                .value(true)
                        ))
                ))
                .withMaxResults(size)
                .build();

        // 쿼리 실행 후 ItemSearchDocument 로 변환하여 리턴
        return elasticsearchOperations.search(query, ItemSearchDocument.class)
                .getSearchHits()
                .stream()
                .map( searchHit -> searchHit.getContent()) // 내용만 꺼내오기
                .map(document -> ItemAutocompleteResponse.from(document)) // 반환값으로 변환
                .toList();
    }

}
