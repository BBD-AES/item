package com.bbd.item.adapter.in.web;

import com.bbd.item.adapter.in.web.dto.ItemAutocompleteResponse;
import com.bbd.item.application.port.in.ItemSearchUseCase;
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

    // TODO(#37): 보안 모듈(bbd-security-core) 적용 후 본 엔드포인트를 관리자 전용으로 제한할 것.
    //   전체 재색인은 ES 인덱스를 삭제 후 재구축하는 파괴적·고비용 작업이므로 반드시 ADMIN 만 호출 가능해야 한다.
    //   적용 절차:
    //     1) build.gradle 의 com.bbd:bbd-security-core 의존성 활성화
    //     2) @EnableMethodSecurity 구성 (Spring Security 6+/Boot 3+ 기준)
    //     3) 아래 bulkCreate() 에 @PreAuthorize("hasRole('ADMIN')") 추가
    //   현재는 보안 모듈이 미적용(주석 처리) 상태라 권한 체크 적용을 보류한다.
    @Operation(summary = "RDB -> ElasticSearch DB 색인 API")
    @PostMapping("/api/v1/items/search/bulk")
    public ResponseEntity<Void> bulkCreate(){
        itemSearchUseCase.bulkCreate();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(summary = "아이템 이름 자동완성 API")
    @GetMapping("/api/v1/items/search/auto")
    public ResponseEntity<List<ItemAutocompleteResponse>> autocomplete(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(itemSearchUseCase.autocomplete(keyword, size));
    }

//    @Operation(summary = "필터 조회 API (1번 ItemController 에서 필터 조회랑 같은 역할)")
//    @GetMapping("/api/v1/items/search/filter")
//    public ResponseEntity<PageResponse> getItemsFilterV2(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "20") Integer size,
//            @RequestParam(required = false, defaultValue = "name") String sortBy,
//            @RequestParam(required = false, defaultValue = "ASC") String direction,
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) Category category,
//            @RequestParam(required = false) Boolean active
//    ) {
//        Sort elasticsearchSort = createElasticsearchSort(sortBy, direction); // 정렬 기준 정하기 (밑에 함수 참고)
//        Pageable pageable = PageRequest.of(page, size, elasticsearchSort);
//        GetItemFilterCommand getItemFilterCommand = new GetItemFilterCommand(name, category, active);
//        Page<ItemResponse> map = itemSearchUseCase.search(pageable, getItemFilterCommand).map(item -> new ItemResponse(item));
//        return ResponseEntity.status(HttpStatus.OK).body(PageResponse.from(map));
//    }


//    private String convertToElasticsearchSortField(String sortBy) {
//        return switch (sortBy) {
//            // case "name" -> "name.keyword"; 한글이 영문보다 정렬 우선순위 높게 필터링 걸기 위해서 따로 뺌
//            case "sku" -> "sku";
//            case "unitPrice" -> "unitPrice";
//            default -> "name.keyword";
//        };
//    }

//    private Sort createElasticsearchSort(String sortBy, String direction) {
//        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
//        if ("name".equals(sortBy)) {
//            return Sort.by(
//                    Sort.Order.asc("nameSortGroup"),               // 한글 → 영문 → 숫자 → 기타 순서 고정
//                    new Sort.Order(sortDirection, "name.keyword"),  // 그룹 안에서 이름 ASC/DESC
//                    Sort.Order.asc("sku")                          // 동일 이름일 때 안정 정렬
//            );
//        }
//        String esSortBy = convertToElasticsearchSortField(sortBy);
//        return Sort.by(
//                new Sort.Order(sortDirection, esSortBy),
//                Sort.Order.asc("sku")
//        );
//
//    }



}
