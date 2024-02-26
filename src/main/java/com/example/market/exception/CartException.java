package com.example.market.exception;

import com.example.market.type.ErrCode;

public class CartException extends CustomException {
    // 기존 생성자 유지
    public CartException(ErrCode errorCode) {
        super(errorCode);
    }

    // 새로운 생성자 추가: 메시지와 에러 코드를 포함
    public CartException(String message, ErrCode errorCode) {
        super(message, errorCode);
    }
}
