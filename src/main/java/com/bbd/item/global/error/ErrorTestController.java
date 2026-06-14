package com.bbd.item.global.error;

import com.bbd.item.global.error.dto.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "99. TestController")
@RestController
@RequestMapping("/api/error")
public class ErrorTestController {

    @Operation(summary = "테스트 응답용 api ProblemDetail 사용")
    @GetMapping
    public ResponseEntity<Void> test1(){
        throw new ApiException(ErrorCode.ERROR_TEST);
    }

}
