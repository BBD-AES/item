package com.bbd.item.global.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TEST_ERROR(HttpStatus.NOT_FOUND, "T001", "찾을 수 없습니다."),


    /**
     * Item 관련된 에러
     */
    ITEM_NOT_FOUNT(HttpStatus.NOT_FOUND, "I404", "존재하지 않는 상품입니다."),
    ITEM_CONFLICT(HttpStatus.CONFLICT, "I409", "이미 존재하는 SKU 입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "V400", "요청 값이 올바르지 않습니다."),

    ;


    private final HttpStatus status;
    private final String code;
    private final String message;
}