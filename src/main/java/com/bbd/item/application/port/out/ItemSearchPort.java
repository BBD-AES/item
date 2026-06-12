package com.bbd.item.application.port.out;

import com.bbd.item.domain.model.item.Item;

import java.util.List;

public interface ItemSearchPort {

    void save(Item item);

    void saveAll(List<Item> items);

    void deleteAll();

}
