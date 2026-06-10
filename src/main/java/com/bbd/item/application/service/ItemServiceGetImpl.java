package com.bbd.item.application.service;

import com.bbd.item.adapter.in.web.dto.ItemListSku;
import com.bbd.item.application.port.in.ItemUseCaseGet;
import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.application.port.in.dto.GetNameCommand;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.domain.model.Item;
import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceGetImpl implements ItemUseCaseGet {

    private final ItemPersistencePort itemPersistencePort;

    @Override
    public Item getItem(String sku) {
        return itemPersistencePort.findBySku(sku)
                .orElseThrow(() -> new ApiException(ErrorCode.ITEM_NOT_FOUND));
    }

    @Override
    public Page<Item> getAll(Pageable pageable) {
        return itemPersistencePort.getAll(pageable);
    }


    @Override
    public Page<Item> getFilterV2(Pageable pageable, GetItemFilterCommand getItemFilterCommand) {
        return itemPersistencePort.getFilterV2(pageable, getItemFilterCommand);
    }

    @Override
    public Page<Item> getFilterV1(Pageable pageable, GetItemFilterCommand getItemFilterCommand) {
        return itemPersistencePort.getFilterV1(pageable, getItemFilterCommand);
    }

    @Override
    public Page<Item> getName(Pageable pageable, GetNameCommand getNameCommand) {
        return itemPersistencePort.getName(pageable, getNameCommand);
    }

    @Override
    public List<Item> getAllInSku(ItemListSku itemListSku) {
        return itemPersistencePort.getAllInSku(itemListSku);
    }
}
