package com.bbd.item.adapter.in.web;

import com.bbd.item.adapter.in.web.dto.CreateItemRequest;
import com.bbd.item.application.port.in.ItemUseCase;
import com.bbd.item.application.port.in.dto.CreateItemCommand;
import com.bbd.item.application.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/item")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemUseCase itemUseCase;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateItemRequest req){

        CreateItemCommand createItemCommand = new CreateItemCommand(
                req.getSku(),
                req.getName(),
                req.getCategory(),
                req.getUnit(),
                req.getSafetyStock(),
                req.getUnitPrice(),
                req.getActive()
        );

        itemUseCase.create(createItemCommand);
        log.info(req.toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }





}
