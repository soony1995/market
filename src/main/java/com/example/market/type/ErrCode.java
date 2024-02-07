package com.example.market.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ErrCode {
    CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "코드를 찾을 수 없습니다."),
    ACCOUNT_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 등록된 아이디입니다!"),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에 문제가 생겼습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_AUTH_KEY(HttpStatus.FORBIDDEN, "잘못된 인증입니다."),
    DATA_ACCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 베이스에 문제가 생겼습니다.");


    private final HttpStatus httpStatus;
    private final String description;
}