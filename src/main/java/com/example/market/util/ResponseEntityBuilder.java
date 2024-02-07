package com.example.market.util;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {
    public static ResponseEntity<Object> buildOkResponse(Object data) {
        return ResponseEntity.ok().body(data);
    }

    public static ResponseEntity<Object> buildOkResponse() {
        return ResponseEntity.ok().body(null);
    }

    public static ResponseEntity<String> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(message);
    }
}
