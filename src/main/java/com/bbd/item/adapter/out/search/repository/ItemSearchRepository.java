package com.bbd.item.adapter.out.search.repository;

import com.bbd.item.adapter.out.search.document.ItemSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemSearchRepository extends ElasticsearchRepository<ItemSearchDocument, String> {

}

