package com.bbd.item.global.error.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        HttpStatus status,
        String code,
        String message
) {}