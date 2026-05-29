package com.bbd.item.application.port.in;

import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.application.port.in.dto.GetNameCommand;
import com.bbd.item.domain.model.Item;

import java.util.List;

public interface ItemUseCaseGet {

    Item getItem(String sku);

    List<Item> getAll();

    List<Item> getFilter(GetItemFilterCommand getItemFilterCommand);

    List<Item> getName(GetNameCommand getNameCommand);

}
