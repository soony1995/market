package com.example.market.utils;

import com.example.market.dto.ErrorResponse;
import com.example.market.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseBuilder {
    public static <T> ResponseEntity<T> buildOkResponse(T data) {
        return ResponseEntity.ok().body(data);
    }

    public static ResponseEntity<ErrorResponse> buildErrResponse(CustomException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }
}
