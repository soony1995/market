package com.example.market.exception;

import com.example.market.type.ErrCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class MemberException extends CustomException {
    public MemberException(ErrCode errorCode) {
        super(errorCode);
    }
}