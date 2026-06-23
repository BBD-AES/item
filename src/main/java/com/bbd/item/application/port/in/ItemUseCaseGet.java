package com.bbd.item.application.port.in;

import com.bbd.item.application.port.in.dto.GetItemsBySkuCommand;
import com.bbd.item.application.dto.ItemSkuLookupResponse;
import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.application.port.in.dto.GetNameCommand;
import com.bbd.item.domain.model.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemUseCaseGet {

    Item getItem(String sku);

    Page<Item> getAll(Pageable pageable);

    Page<Item> getFilterV2(Pageable pageable, GetItemFilterCommand getItemFilterCommand);

    Page<Item> getFilterV1(Pageable pageable, GetItemFilterCommand getItemFilterCommand);

    Page<Item> getName(Pageable pageable, GetNameCommand getNameCommand);

    List<ItemSkuLookupResponse> getAllInSku(GetItemsBySkuCommand getItemsBySkuCommand);
}
