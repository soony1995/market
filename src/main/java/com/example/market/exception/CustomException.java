package com.example.market.exception;

import com.example.market.type.ErrCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomException extends RuntimeException {
    private final ErrCode errorCode;

    protected CustomException(ErrCode errorCode) {
        this.errorCode = errorCode;
    }
}