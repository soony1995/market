package com.example.market.exception;

import com.example.market.type.ErrCode;
import lombok.Getter;

@Getter
public class OrderItemException extends CustomException {
    public OrderItemException(ErrCode errorCode) {
        super(errorCode);
    }
}