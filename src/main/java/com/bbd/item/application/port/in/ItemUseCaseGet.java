package com.bbd.item.application.port.in;

import com.bbd.item.domain.model.Item;

import java.util.List;

public interface ItemUseCaseGet {

    Item getItem(String sku);

    List<Item> getAll();

}
