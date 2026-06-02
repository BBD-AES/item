package com.bbd.item.adapter.in.web;

import com.bbd.item.adapter.in.web.dto.CreateItemRequest;
import com.bbd.item.adapter.in.web.dto.UpdateItemRequest;
import com.bbd.item.adapter.in.web.dto.UpdateNameItemRequest;
import com.bbd.item.adapter.in.web.dto.UpdatePriceItemRequest;
import com.bbd.item.application.port.in.ItemUseCaseCreate;
import com.bbd.item.application.port.in.ItemUseCaseGet;
import com.bbd.item.application.port.in.ItemUseCaseUpdate;
import com.bbd.item.application.port.in.dto.*;
import com.bbd.item.domain.model.Category;
import com.bbd.item.domain.model.Item;
import com.bbd.item.domain.model.Unit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1. ItemController")
@RequestMapping("/api/v1/items")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemUseCaseCreate itemUseCaseCreate;
    private final ItemUseCaseGet itemUseCaseGet;
    private final ItemUseCaseUpdate itemUseCaseUpdate;

    /**
     * Post
     * 1. 상품 생성 API
     *
     * Patch
     * 1. 상품 이름 수정 API
     * 2. 상품 단가 수정 API
     * 3. 상품 이름 & 단가 수정 API
     *
     * Get
     * 1. Sku 단건 조회 API
     * 2. 전체 조회 API (pageable)
     * 3. 필터 조회 API (pageable)
     * 4. 이름 조회 API (pageable)
     */

    @Operation(summary = "생성 API (권한 체크)")
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

    @Operation(summary = "상품 이름 수정 API (관리지만 가능)")
    @PatchMapping("/{sku}/name")
    public ResponseEntity<Void> updateName(@PathVariable String sku, @RequestBody UpdateNameItemRequest req) {
        UpdateNameCommand updateNameCommand = new UpdateNameCommand(sku, req.getName());
        itemUseCaseUpdate.updateName(updateNameCommand);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "상품 단가 수정 API (권한 체크)")
    @PatchMapping("/{sku}/price")
    public ResponseEntity<Void> updatePrice(@PathVariable String sku, @RequestBody UpdatePriceItemRequest req) {
        UpdatePriceCommand updatePriceCommand = new UpdatePriceCommand(sku, req.getUnitPrice());
        itemUseCaseUpdate.updatePrice(updatePriceCommand);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "상품 단가 및 이름 수정 API (권한 체크)")
    @PatchMapping("/{sku}")
    public ResponseEntity<Void> update(@PathVariable String sku, @RequestBody UpdateItemRequest req) {
        UpdateCommand updateCommand = new UpdateCommand(sku, req.getName(), req.getUnitPrice());
        itemUseCaseUpdate.update(updateCommand);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Sku 단건 조회 API")
    @GetMapping("/{sku}")
    public ResponseEntity<Item> getItem(@PathVariable String sku) {
        Item item = itemUseCaseGet.getItem(sku);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @Operation(summary = "전체 조회 API")
    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems(Pageable pageable) {
        List<Item> items = itemUseCaseGet.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @Operation(summary = "필터 조회")
    @GetMapping
    public ResponseEntity<List<Item>> getItemsFilter(
            Pageable pageable,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Unit unit,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
            ){
        GetItemFilterCommand getItemFilterCommand = new GetItemFilterCommand(category, active, unit, minPrice, maxPrice);
        List<Item> items = itemUseCaseGet.getFilter(pageable, getItemFilterCommand);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @Operation(summary = "이름으로 조회")
    @GetMapping("/name")
    public ResponseEntity<List<Item>> getItemsContainsName(
            Pageable pageable,
            @RequestParam(required = false) String name
    ){
        GetNameCommand getNameCommand = new GetNameCommand(name);
        List<Item> items = itemUseCaseGet.getName(pageable, getNameCommand);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }


}
