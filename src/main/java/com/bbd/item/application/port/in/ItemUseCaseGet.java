package com.bbd.item.application.port.in;

import com.bbd.item.domain.model.Item;

public interface ItemUseCaseGet {

    Item getItem(String sku);

}
