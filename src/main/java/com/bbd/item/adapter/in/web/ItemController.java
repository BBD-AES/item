package com.bbd.item.adapter.in.web;

import com.bbd.item.adapter.in.web.dto.CreateItemRequest;
import com.bbd.item.adapter.in.web.dto.UpdateNameItemRequest;
import com.bbd.item.adapter.in.web.dto.UpdatePriceItemRequest;
import com.bbd.item.application.port.in.ItemUseCaseCreate;
import com.bbd.item.application.port.in.ItemUseCaseGet;
import com.bbd.item.application.port.in.ItemUseCaseUpdate;
import com.bbd.item.application.port.in.dto.CreateItemCommand;
import com.bbd.item.application.port.in.dto.UpdateNameCommand;
import com.bbd.item.application.port.in.dto.UpdatePriceCommand;
import com.bbd.item.domain.model.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "1. ItemController")
@RequestMapping("/api/v1/items")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemUseCaseCreate itemUseCaseCreate;
    private final ItemUseCaseGet itemUseCaseGet;
    private final ItemUseCaseUpdate itemUseCaseUpdate;

    @Operation(summary = "생성 API (관리자만 가능) ")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateItemRequest req) {
        CreateItemCommand createItemCommand = new CreateItemCommand(
                req.getSku(),
                req.getName(),
                req.getCategory(),
                req.getUnit(),
                req.getSafetyStock(),
                req.getUnitPrice(),
                req.getActive()
        );
        itemUseCaseCreate.create(createItemCommand);
        log.info(req.toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "SKU 로 단건조회 API")
    @GetMapping("/{sku}")
    public ResponseEntity<Item> getItem(@PathVariable String sku) {
        Item item = itemUseCaseGet.getItem(sku);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @Operation(summary = "상품 이름 수정 API (관리지만 가능)")
    @PatchMapping("/{sku}/name")
    public ResponseEntity<Void> updateName(@PathVariable String sku, @RequestBody UpdateNameItemRequest req) {
        UpdateNameCommand updateNameCommand = new UpdateNameCommand(sku, req.getName());
        itemUseCaseUpdate.updateName(updateNameCommand);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "상품 단가 수정 API (관리자만 가능)")
    @PatchMapping("/{sku}/price")
    public ResponseEntity<Void> updatePrice(@PathVariable String sku, @RequestBody UpdatePriceItemRequest req) {
        UpdatePriceCommand updatePriceCommand = new UpdatePriceCommand(sku, req.getUnitPrice());
        itemUseCaseUpdate.updatePrice(updatePriceCommand);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
