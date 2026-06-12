package com.bbd.item.adapter.out.search;

import com.bbd.item.adapter.out.search.document.ItemSearchDocument;
import com.bbd.item.adapter.out.search.repository.ItemSearchRepository;
import com.bbd.item.application.port.out.ItemSearchPort;
import com.bbd.item.domain.model.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemSearchAdapter implements ItemSearchPort {

    private final ItemSearchRepository itemSearchRepository;

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


}
