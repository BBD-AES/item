package com.bbd.item.adapter.in.web.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 무조건 from 을 통해서 만들기
public class PageResponse {

    private final List<ItemResponse> content;

    private final int page;

    private final int size;

    private final long totalElements;

    private final int totalPages;

    private final boolean first;

    private final boolean last;

    private final boolean hasNext;

    private final boolean hasPrevious;

    public static PageResponse from(Page<ItemResponse> page) {
        return new PageResponse(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

}
