package com.example.market.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


//TODO:  400 error = 클라이언트 잘못 404 -> 클라이언트 너가 없는 url로 친거야. 401은 권한이 없구나. 등등
// TODO: 500 error = 서버잘못
// TODO: 200 은 성공류(200, 204)
@Getter
@RequiredArgsConstructor
public enum ErrCode {
    LOGIN_ERROR(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다."),
    ACCOUNT_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 등록된 아이디입니다!"),
    ACCOUNT_NOT_EXIST(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."), // TODO: 404는 없는 url
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에 문제가 생겼습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_AUTH_KEY(HttpStatus.FORBIDDEN, "잘못된 인증입니다."),
    DATA_ACCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 베이스에 문제가 생겼습니다."),
    ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST, "찾는 아이템이 존재하지 않습니다."),
    ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "찾는 주문이 존재하지 않습니다."),
    CART_NOT_FOUND(HttpStatus.BAD_REQUEST, "장바구니가 존재하지 않습니다."),
    ITEM_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 등록된 상품입니다."),
    ITEM_NOT_DELETED(HttpStatus.BAD_REQUEST, "상품 삭제에 실패했습니다."),
    STOCK_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "재고가 부족합니다.");


    private final HttpStatus httpStatus;
    private final String description;
}