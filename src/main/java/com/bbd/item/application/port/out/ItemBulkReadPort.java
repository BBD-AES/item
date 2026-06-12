package com.bbd.item.application.port.out;

import com.bbd.item.domain.model.item.Item;

import java.util.List;

public interface ItemBulkReadPort {

    List<Item> findFirstBatch(int size);

    List<Item> findNextBatch(String lastSku, int size);

}