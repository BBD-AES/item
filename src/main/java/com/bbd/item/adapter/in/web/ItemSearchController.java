package com.bbd.item.adapter.in.web;

import com.bbd.item.adapter.in.web.dto.ItemAutocompleteResponse;
import com.bbd.item.application.port.in.ItemSearchUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @PostMapping("/api/v1/items/searchBulk")
    public ResponseEntity<Void> bulkCreate(){
        itemSearchUseCase.bulkCreate();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "아이템 이름 자동완성 API")
    @GetMapping("/api/v1/items/auto")
    public ResponseEntity<List<ItemAutocompleteResponse>> autocomplete(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(itemSearchUseCase.autocomplete(keyword, size));
    }





}
