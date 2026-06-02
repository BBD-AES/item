package com.bbd.item.application.service;

import com.bbd.item.application.port.in.ItemUseCaseGet;
import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.application.port.in.dto.GetNameCommand;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.domain.model.Item;
import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new ApiException(ErrorCode.ITEM_NOT_FOUNT));
    }

    @Override
    public List<Item> getAll(Pageable pageable) {
        return itemPersistencePort.getAll(pageable);
    }

    @Override
    public List<Item> getFilter(Pageable pageable, GetItemFilterCommand getItemFilterCommand) {
        return itemPersistencePort.getFilter(pageable, getItemFilterCommand);
    }

    @Override
    public List<Item> getName(Pageable pageable, GetNameCommand getNameCommand) {
        return itemPersistencePort.getName(pageable, getNameCommand);
    }
}
