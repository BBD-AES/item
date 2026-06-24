package com.bbd.item.application.port.out;

import com.bbd.item.adapter.in.web.dto.ItemAutocompleteResponse;
import com.bbd.item.domain.model.item.Item;
import com.bbd.item.domain.model.item.SourcingType;

import java.util.List;

public interface ItemSearchPort {

    void save(Item item);

    void saveAll(List<Item> items);

    void deleteAll();

    List<ItemAutocompleteResponse> autocomplete(String keyword, int size, boolean active, SourcingType sourcingType);

}
