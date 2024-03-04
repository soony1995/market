package com.example.market.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ErrCode {
    //MEMBER
    MEMBER_NOT_AUTHORIZATION(HttpStatus.UNAUTHORIZED,"권한이 없습니다."),
    MEMBER_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 등록된 아이디입니다!"),
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    MEMBER_INVALID_AUTH_KEY(HttpStatus.FORBIDDEN, "잘못된 인증입니다."),

    //ORDER
    ORDER_NOT_EXIST(HttpStatus.NOT_FOUND, "찾는 주문이 존재하지 않습니다."),

    //CART
    CART_NOT_EXIST(HttpStatus.NOT_FOUND, "장바구니가 존재하지 않습니다."),
    CART_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"수량을 업데이트 할 수 없습니다."),

    //ITEM
    ITEM_NOT_EXIST(HttpStatus.NOT_FOUND, "찾는 아이템이 존재하지 않습니다."),
    ITEM_STOCK_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "재고가 부족합니다."),
    ITEM_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 등록된 상품입니다."),
    ITEM_NOT_DELETED(HttpStatus.BAD_REQUEST, "상품 삭제에 실패했습니다."),

    //SERVER
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에 문제가 생겼습니다.");


    private final HttpStatus httpStatus;
    private final String description;
}