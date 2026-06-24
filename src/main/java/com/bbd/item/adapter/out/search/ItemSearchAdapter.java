package com.bbd.item.adapter.out.search;

import com.bbd.item.adapter.in.web.dto.ItemAutocompleteResponse;
import com.bbd.item.adapter.out.search.document.ItemSearchDocument;
import com.bbd.item.adapter.out.search.repository.ItemSearchRepository;
import com.bbd.item.application.port.out.ItemSearchPort;
import com.bbd.item.domain.model.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
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
    public List<ItemAutocompleteResponse> autocomplete(String keyword, int size, boolean active) {
        String queryJson = """
            {
              "bool": {
                "should": [
                  {
                    "match": {
                      "sku.autocomplete": {
                        "query": "%s",
                        "boost": 2.0
                      }
                    }
                  },
                  {
                    "match": {
                      "name.autocomplete": "%s"
                    }
                  }
                ],
                "minimum_should_match": 1,
                "filter": [
                  {
                    "term": {
                      "active": %s
                    }
                  }
                ]
              }
            }
            """.formatted(keyword, keyword, active);

        Query query = new StringQuery(queryJson);
        query.setPageable(PageRequest.of(0, size));

        // 쿼리 실행 후 ItemSearchDocument 로 변환하여 리턴
        return elasticsearchOperations.search(query, ItemSearchDocument.class)
                .getSearchHits()
                .stream()
                .map( searchHit -> searchHit.getContent()) // 내용만 꺼내오기
                .map(document -> ItemAutocompleteResponse.from(document)) // 반환값으로 변환
                .toList();
    }

}
