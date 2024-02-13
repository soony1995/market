package com.example.market.exception;

import com.example.market.type.ErrCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ItemException extends RuntimeException {
    private final ErrCode errorCode;
    private final String errorMessage;
    private final HttpStatus statusCode;

    public ItemException(ErrCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
        this.statusCode = errorCode.getHttpStatus();
    }
}