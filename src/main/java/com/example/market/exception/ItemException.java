package com.example.market.exception;

import com.example.market.exception.CustomException;
import com.example.market.type.ErrCode;
import lombok.Getter;

@Getter
public class ItemException extends CustomException {
    public ItemException(ErrCode errorCode) {
        super(errorCode);
    }
}