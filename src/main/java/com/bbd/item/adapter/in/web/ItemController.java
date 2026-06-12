package com.bbd.item.adapter.in.web;

import com.bbd.item.adapter.in.web.dto.*;
import com.bbd.item.application.port.in.ItemUseCaseCreate;
import com.bbd.item.application.port.in.ItemUseCaseGet;
import com.bbd.item.application.port.in.ItemUseCaseUpdate;
import com.bbd.item.application.port.in.dto.CreateItemCommand;
import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.application.port.in.dto.UpdatePriceCommand;
import com.bbd.item.domain.model.item.Category;
import com.bbd.item.domain.model.item.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1. ItemController")
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
     * <p>
     * Patch
     * 1. 상품 이름 수정 API
     * 2. 상품 단가 수정 API
     * 3. 상품 이름 & 단가 수정 API
     * <p>
     * Get
     * 1. Sku 단건 조회 API
     * 2. 전체 조회 API (pageable)
     * 3. 필터 조회 API (pageable)
     * 4. 이름 조회 API (pageable)
     */


//    @RequireRole({UserRole.HQ_MANAGER, UserRole.HQ_STAFF})
    @Operation(summary = "생성 API (권한 체크)")
    @PostMapping("/api/v1/items")
    @Valid
    public ResponseEntity<Void> create(@RequestBody CreateItemRequest req) {
        CreateItemCommand createItemCommand = new CreateItemCommand(
                req.getSku(),
                req.getName(),
                req.getCategory(),
                req.getUnit(),
                req.getSafetyStock(),
                req.getUnitPrice(),
                req.getActive(),
                req.getSourcingType()
        );
        itemUseCaseCreate.create(createItemCommand);
        log.info(req.toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * PATCH 구분선
     * ======================================
     */
    @Operation(summary = "상품 단가 수정 API (권한 체크 필요)")
    @PatchMapping("/api/v1/items/{sku}/price")
    public ResponseEntity<Void> updatePrice(@NotBlank @PathVariable String sku, @Valid @RequestBody UpdatePriceItemRequest req) {
        UpdatePriceCommand updatePriceCommand = new UpdatePriceCommand(sku, req.getUnitPrice());
        itemUseCaseUpdate.updatePrice(updatePriceCommand);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * GET 구분선
     * ======================================
     */
    @Operation(summary = "Sku 단건 조회 API")
    @GetMapping("/api/v1/items/{sku}")
    public ResponseEntity<ItemResponse> getItem(@NotBlank @PathVariable String sku) {
        Item item = itemUseCaseGet.getItem(sku);
        ItemResponse itemResponse = new ItemResponse(item);
        return ResponseEntity.status(HttpStatus.OK).body(itemResponse);
    }

    @Operation(summary = "Sku 포함 여러개 조회 API")
    @GetMapping("/api/v1/items")
    public ResponseEntity<List<ItemResponse>> getItems(@RequestBody ItemListSku itemListSku) {
        List<Item> allInSku = itemUseCaseGet.getAllInSku(itemListSku);
        List<ItemResponse> list = allInSku.stream()
                .map(item -> new ItemResponse(item))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @Operation(summary = "필터 조회API (category & name & active 필터), (정렬은 이름 & sku 만 가능)")
    @GetMapping("/api/v1/items/filter")
    public ResponseEntity<PageResponse> getItemsFilterV2(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Boolean active
    ) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        GetItemFilterCommand getItemFilterCommand = new GetItemFilterCommand(name, category, active);
        Page<ItemResponse> map = itemUseCaseGet.getFilterV2(pageable, getItemFilterCommand).map(item -> new ItemResponse(item));
        PageResponse pageResponse = PageResponse.from(map);
        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }

//    @Operation(summary = "상품 이름 수정 API / (사용 중지)")
//    @PatchMapping("/{sku}/name")
//    public ResponseEntity<Void> updateName(@NotBlank @PathVariable String sku, @Valid @RequestBody UpdateNameItemRequest req) {
//        UpdateNameCommand updateNameCommand = new UpdateNameCommand(sku, req.getName());
//        itemUseCaseUpdate.updateName(updateNameCommand);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }


//    @Operation(summary = "상품 단가 및 이름 수정 API (사용 중지)")
//    @PatchMapping("/{sku}")
//    public ResponseEntity<Void> update(@NotBlank @PathVariable String sku, @Valid @RequestBody UpdateItemRequest req) {
//        UpdateCommand updateCommand = new UpdateCommand(sku, req.getName(), req.getUnitPrice());
//        itemUseCaseUpdate.update(updateCommand);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

//    @Operation(summary = "전체 조회 API")
//    @GetMapping("/api/v1/items/all")
//    public ResponseEntity<PageResponse> getAllItemsV2(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "20") Integer size,
//            @RequestParam(required = false, defaultValue = "sku") String sortBy,
//            @RequestParam(required = false, defaultValue = "ASC") String direction
//    ) {
//        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
//        Page<ItemResponse> map = itemUseCaseGet.getAll(pageable).map(item -> new ItemResponse(item));
//        PageResponse pageResponse = PageResponse.from(map);
//        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
//    }

//    @Operation(summary = "필터 조회API (category & name & active 필터), (정렬은 이름 & sku 만 가능)")
//    @GetMapping("/api/v1/items/old")
//    public ResponseEntity<PageResponse> getItemsFilterV1(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "20") Integer size,
//            @RequestParam(required = false, defaultValue = "name") String sortBy,
//            @RequestParam(required = false, defaultValue = "ASC") String direction,
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) Category category,
//            @RequestParam(required = false) Boolean active
//    ) {
//        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
//        GetItemFilterCommand getItemFilterCommand = new GetItemFilterCommand(name, category, active);
//        Page<ItemResponse> map = itemUseCaseGet.getFilterV1(pageable, getItemFilterCommand).map(item -> new ItemResponse(item));
//        PageResponse pageResponse = PageResponse.from(map);
//        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
//    }

//    @Operation(summary = "이름으로 조회")
//    @GetMapping("/name")
//    public ResponseEntity<PageResponse> getItemsContainsNameV2(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "20") Integer size,
//            @RequestParam(required = false, defaultValue = "sku") String sortBy,
//            @RequestParam(required = false, defaultValue = "ASC") String direction,
//            @RequestParam(required = false) String name
//    ) {
//        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
//        GetNameCommand getNameCommand = new GetNameCommand(name);
//        Page<Item> pageItem = itemUseCaseGet.getName(pageable, getNameCommand);
//        Page<ItemResponse> map = pageItem.map(item -> new ItemResponse(item));
//        PageResponse pageResponse = PageResponse.from(map);
//        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
//    }


}
