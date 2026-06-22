package com.bbd.item.application.service;

import com.bbd.item.application.port.in.dto.GetItemsBySkuCommand;
import com.bbd.item.application.dto.ItemSkuLookupResponse;
import com.bbd.item.application.port.in.ItemUseCaseGet;
import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.application.port.in.dto.GetNameCommand;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.domain.model.item.Item;
import com.bbd.item.global.error.ApiException;
import com.bbd.item.global.error.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public List<ItemSkuLookupResponse> getAllInSku(GetItemsBySkuCommand getItemsBySkuCommand) {
        // where in 쿼리로 조회
        List<Item> allInSku = itemPersistencePort.getAllInSku(getItemsBySkuCommand);
        Map<String, Item> itemMap = allInSku.stream().collect(Collectors.toMap(i -> i.getSku(), Function.identity()));

        return getItemsBySkuCommand.getSkuList().stream()
                .map(sku -> {
                    Item item = itemMap.get(sku);
                    if (item == null) {
                        return ItemSkuLookupResponse.notFound(sku);
                    }
                    return ItemSkuLookupResponse.found(item);
                })
                .toList();
    }

}
