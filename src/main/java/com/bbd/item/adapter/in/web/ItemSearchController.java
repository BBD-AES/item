package com.bbd.item.adapter.in.web;

import com.bbd.item.adapter.in.web.dto.ItemAutocompleteResponse;
import com.bbd.item.adapter.in.web.dto.ItemResponse;
import com.bbd.item.adapter.in.web.dto.PageResponse;
import com.bbd.item.application.port.in.ItemSearchUseCase;
import com.bbd.item.application.port.in.dto.GetItemFilterCommand;
import com.bbd.item.domain.model.item.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "2. ItemSearchController")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemSearchController {

    private final ItemSearchUseCase itemSearchUseCase;

    // TODO : 관리자만 가능하게끔 Role 체크 (ElasticSearch 색인 동기화)
    @Operation(summary = "RDB -> ElasticSearch DB 색인 API")
    @PostMapping("/api/v1/items/search/bulk")
    public ResponseEntity<Void> bulkCreate(){
        itemSearchUseCase.bulkCreate();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "아이템 이름 자동완성 API")
    @GetMapping("/api/v1/items/search/auto")
    public ResponseEntity<List<ItemAutocompleteResponse>> autocomplete(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(itemSearchUseCase.autocomplete(keyword, size));
    }

    @Operation(summary = "필터 조회 API (1번 ItemController 에서 필터 조회랑 같은 역할)")
    @GetMapping("/api/v1/items/search/filter")
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
        String esSortBy = convertToElasticsearchSortField(sortBy);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, esSortBy));
        GetItemFilterCommand getItemFilterCommand = new GetItemFilterCommand(name, category, active);
        Page<ItemResponse> map = itemSearchUseCase.search(pageable, getItemFilterCommand).map(item -> new ItemResponse(item));
        return ResponseEntity.status(HttpStatus.OK).body(PageResponse.from(map));
    }


    private String convertToElasticsearchSortField(String sortBy) {
        return switch (sortBy) {
            case "name" -> "name.keyword";
            case "sku" -> "sku";
            case "unitPrice" -> "unitPrice";
            default -> "name.keyword";
        };
    }




}
