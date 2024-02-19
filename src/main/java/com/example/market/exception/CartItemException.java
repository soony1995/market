package com.example.market.exception;
import com.example.market.type.ErrCode;
import lombok.Getter;

@Getter
public class CartItemException extends CustomException {
    public CartItemException(ErrCode errorCode) {
        super(errorCode);
    }
}