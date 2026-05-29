package com.bbd.item.application.service;

import com.bbd.item.application.port.in.ItemUseCase;
import com.bbd.item.application.port.in.dto.CreateItemCommand;
import com.bbd.item.application.port.out.ItemPersistencePort;
import com.bbd.item.domain.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService implements ItemUseCase {

    private final ItemPersistencePort itemPersistencePort;

    @Transactional
    @Override
    public void create(CreateItemCommand req)
    {
        Item item = new Item(
                req.getSku(),
                req.getName(),
                req.getCategory(),
                req.getUnit(),
                req.getSafetyStock(),
                req.getUnitPrice(),
                req.getActive());
        itemPersistencePort.save(item);
    }

}
