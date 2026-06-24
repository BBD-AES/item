package com.bbd.item.adapter.in.web;

import com.bbd.item.adapter.in.web.dto.ItemAutocompleteResponse;
import com.bbd.item.application.port.in.ItemSearchUseCase;
import com.bbd.securitycore.adapter.in.annotation.RequireRole;
import com.bbd.securitycore.domain.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @RequireRole(UserRole.ADMIN)
    @Operation(summary = "RDB -> ElasticSearch DB 색인 API")
    @PostMapping("/api/v1/items/search/bulk")
    public ResponseEntity<Void> bulkCreate() {
        itemSearchUseCase.bulkCreate();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(summary = "아이템 이름 자동완성 API")
    @GetMapping("/api/v1/items/search/auto")
    public ResponseEntity<List<ItemAutocompleteResponse>> autocomplete(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "true") boolean active
    ) {
        return ResponseEntity.ok(itemSearchUseCase.autocomplete(keyword, size, active));
    }


}
