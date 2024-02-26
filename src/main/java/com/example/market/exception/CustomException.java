package com.example.market.exception;

import com.example.market.type.ErrCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final ErrCode errorCode;

    public CustomException(ErrCode errorCode) {
        this.errorCode = errorCode;
    }

    public CustomException(String message, ErrCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(String message, Throwable cause, ErrCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CustomException(ErrCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public CustomException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace,
            ErrCode errorCode
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}
