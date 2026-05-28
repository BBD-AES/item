package com.bbd.item.application.service;

import com.bbd.item.adapter.in.web.dto.CreateItemRequest;
import com.bbd.item.application.port.in.ItemUseCase;
import org.springframework.stereotype.Service;

@Service
public class ItemService implements ItemUseCase {

    @Override
    public void create(CreateItemRequest req) {
        return;
    }

}
