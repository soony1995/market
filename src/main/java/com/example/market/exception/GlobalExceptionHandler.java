package com.example.market.exception;

import com.example.market.dto.ErrorResponse;
import com.example.market.type.ErrCode;
import com.sun.nio.sctp.IllegalReceiveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> memberExHandle(MemberException e) {
        return buildResponseEntity(e.getMessage(), e.getErrorCode(), e.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodExHandle(MethodArgumentNotValidException e) {
        return buildResponseEntity(e.getMessage(), ErrCode.BAD_REQUEST, ErrCode.BAD_REQUEST.getHttpStatus());
    }

    @ExceptionHandler(IllegalReceiveException.class)
    public ResponseEntity<ErrorResponse> illegalExHandle(IllegalReceiveException e) {
        return buildResponseEntity(e.getMessage(), ErrCode.BAD_REQUEST, ErrCode.BAD_REQUEST.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exHandle(Exception e) {
        return buildResponseEntity(e.getMessage(), ErrCode.INTERNAL_SERVER_ERROR, ErrCode.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<ErrorResponse> itemExHandle(Exception e) {
        return buildResponseEntity(e.getMessage(), ErrCode.ITEM_NOT_FOUND, ErrCode.ITEM_NOT_FOUND.getHttpStatus());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> dataAccessExHandle(DataAccessException e) {
        // 적절한 HTTP 상태 코드와 함께 에러 메시지를 반환
        return buildResponseEntity(e.getMessage(), ErrCode.DATA_ACCESS_ERROR, ErrCode.DATA_ACCESS_ERROR.getHttpStatus());
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(String logMessage, ErrCode errorCode, HttpStatus status) {
        log.error(logMessage);
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return ResponseEntity.status(status).body(errorResponse);
    }
}