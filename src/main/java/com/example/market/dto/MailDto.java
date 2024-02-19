package com.example.market.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private String content;
}