package com.example.market.exception;

import com.example.market.dto.ErrorResponse;
import com.example.market.exception.CustomException; // 가정: 모든 커스텀 예외의 공통 인터페이스
import com.example.market.type.ErrCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return buildResponseEntity(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(ErrCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(CustomException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }
}