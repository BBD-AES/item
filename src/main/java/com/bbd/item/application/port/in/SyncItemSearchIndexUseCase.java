package com.bbd.item.application.port.in;

import com.bbd.item.adapter.in.kafka.ItemPriceChangedEvent;


public interface SyncItemSearchIndexUseCase {

    void syncPriceChanged(ItemPriceChangedEvent event);

}
