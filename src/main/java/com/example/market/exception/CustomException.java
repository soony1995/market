package com.example.market.exception;

import com.example.market.type.ErrCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final ErrCode errorCode;
    private final String message;

    public CustomException(ErrCode errorCode) {
        this.message = errorCode.getDescription();
        this.errorCode = errorCode;
    }
}
