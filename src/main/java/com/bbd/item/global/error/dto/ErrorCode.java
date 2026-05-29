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
    ITEM_NOT_FOUNT(HttpStatus.NOT_FOUND, "I404", "존재하지 않는 상품입니다.."),

    ;


    private final HttpStatus status;
    private final String code;
    private final String message;
}