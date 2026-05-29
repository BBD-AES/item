package com.bbd.item.application.port.out;

import com.bbd.item.domain.model.Item;

public interface ItemPersistencePort {

    void save(Item item);

}
