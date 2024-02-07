package com.example.market.dto;

import com.example.market.type.ErrCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final HttpStatus status;
    private final ErrCode errorCode;
    private final String errorMessage;

    public ErrorResponse(ErrCode errorCode) {
        this.status = errorCode.getHttpStatus();
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}