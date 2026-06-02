package com.bbd.item.application.port.in;

import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.application.port.in.dto.GetNameCommand;
import com.bbd.item.domain.model.Item;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemUseCaseGet {

    Item getItem(String sku);

    List<Item> getAll(Pageable pageable);

    List<Item> getFilter(Pageable pageable, GetItemFilterCommand getItemFilterCommand);

    List<Item> getName(Pageable pageable, GetNameCommand getNameCommand);

}
