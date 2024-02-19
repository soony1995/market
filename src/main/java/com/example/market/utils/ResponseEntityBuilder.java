package com.example.market.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {

    // 제네릭 타입을 쓰는 이유 공부해 보시면 좋습니다.
    public static <T> ResponseEntity<T> buildOkResponse(T data) {
        return ResponseEntity.ok().body(data);
    }

    public static ResponseEntity<Object> buildOkResponse() {
        return ResponseEntity.ok().body(null);
    }

    public static ResponseEntity<String> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(message);
    }
}
