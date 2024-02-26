package com.example.market.dto.item;

import org.springframework.http.HttpStatus;

public class ResponseData {
    private Code code;
    private HttpStatus status;


    enum Code {
        SUCCESS, FAIL
    }
}
