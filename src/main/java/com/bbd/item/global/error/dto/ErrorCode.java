package com.bbd.item.global.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TEST_ERROR(HttpStatus.NOT_FOUND, "T001", "찾을 수 없습니다."),

    /**
     * SALES 관련된 에러
     */
    SALES_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "SO001", "출고 요청을 찾을 수 없습니다."),
    SALES_ORDER_NOT_EDITABLE(HttpStatus.CONFLICT, "SO002", "REQUESTED 상태의 출고 요청만 수정할 수 있습니다."),
    SALES_ORDER_FORBIDDEN_WAREHOUSE(HttpStatus.FORBIDDEN, "SO003", "본인 소속 창고의 출고 요청만 접근할 수 있습니다."),
    SALES_ORDER_NOT_DECIDABLE(HttpStatus.CONFLICT, "SO004", "SUBMITTED 상태의 출고 요청만 승인/반려할 수 있습니다."),
    SALES_ORDER_NOT_RECEIVABLE(HttpStatus.CONFLICT, "SO005", "IN_FULFILLMENT 상태의 출고 요청만 수령할 수 있습니다."),
    SALES_ORDER_REJECT_REASON_REQUIRED(HttpStatus.BAD_REQUEST, "SO006", "반려 사유는 필수입니다."),
    SALES_ORDER_FORBIDDEN_ROLE(HttpStatus.FORBIDDEN, "SO007", "해당 작업을 수행할 권한이 없습니다."),
    SALES_ORDER_NOT_SUBMITTABLE(HttpStatus.CONFLICT, "SO008", "REQUESTED 상태의 출고 요청만 HQ로 제출할 수 있습니다."),
    SALES_ORDER_NOT_CANCELABLE(HttpStatus.CONFLICT, "SO009", "REQUESTED 또는 SUBMITTED 상태에서만 취소할 수 있습니다."),
    SALES_ORDER_NOT_FULFILLABLE(HttpStatus.CONFLICT, "SO010", "BACKORDERED 상태의 출고 요청만 충족 처리할 수 있습니다."),
    AUTH_HEADER_REQUIRED(HttpStatus.UNAUTHORIZED, "AUTH001", "인증 헤더가 필요합니다."),
    AUTH_ROLE_INVALID(HttpStatus.BAD_REQUEST, "AUTH002", "알 수 없는 역할입니다."),


    /**
     * Item 관련된 에러
     */
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "I001", "존재하지 않는 상품입니다."),
    ITEM_SKU_CONFLICT(HttpStatus.CONFLICT, "I002", "이미 존재하는 SKU입니다."),
    ITEM_NAME_REQUIRED(HttpStatus.BAD_REQUEST, "I003", "상품명은 필수입니다."),
    ITEM_SKU_REQUIRED(HttpStatus.BAD_REQUEST, "I004", "SKU는 필수입니다."),
    ITEM_CATEGORY_REQUIRED(HttpStatus.BAD_REQUEST, "I005", "카테고리는 필수입니다."),
    ITEM_UNIT_REQUIRED(HttpStatus.BAD_REQUEST, "I006", "단위는 필수입니다."),
    ITEM_PRICE_INVALID(HttpStatus.BAD_REQUEST, "I007", "상품 단가는 0원 이상이어야 합니다."),
    ITEM_SAFETY_STOCK_INVALID(HttpStatus.BAD_REQUEST, "I008", "안전 재고는 0개 이상이어야 합니다."),
    ITEM_NOT_ACTIVE(HttpStatus.CONFLICT, "I009", "비활성화된 상품은 사용할 수 없습니다."),
    ITEM_ALREADY_ACTIVE(HttpStatus.CONFLICT, "I010", "이미 활성화된 상품입니다."),
    ITEM_ALREADY_INACTIVE(HttpStatus.CONFLICT, "I011", "이미 비활성화된 상품입니다."),
    ITEM_NOT_UPDATABLE(HttpStatus.CONFLICT, "I012", "현재 상태에서는 상품 정보를 수정할 수 없습니다."),
    ITEM_NOT_DELETABLE(HttpStatus.CONFLICT, "I013", "사용 이력이 있는 상품은 삭제할 수 없습니다."),
    ITEM_CATEGORY_INVALID(HttpStatus.BAD_REQUEST, "I014", "알 수 없는 상품 카테고리입니다."),
    ITEM_UNIT_INVALID(HttpStatus.BAD_REQUEST, "I015", "알 수 없는 상품 단위입니다."),
    ITEM_FORBIDDEN_ROLE(HttpStatus.FORBIDDEN, "I016", "상품을 관리할 권한이 없습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "I017", "요청 값이 올바르지 않습니다."),
    ITEM_SKU_NOTFOUND(HttpStatus.BAD_REQUEST, "I018", "존재하지 않는 SKU가 포함되어 있습니다."),
    ITEM_UPDATE_PRICE_FAIL(HttpStatus.CONFLICT, "I019", "상품 가격 변경에 실패했습니다."),
    ITEM_EVENT_NOT_PUBLISH(HttpStatus.INTERNAL_SERVER_ERROR, "I020", "가격 변경 이벤트 발행에 실패했습니다."),
    OUTBOX_EVENT_CREATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "I021", "Outbox 이벤트 생성에 실패했습니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

}