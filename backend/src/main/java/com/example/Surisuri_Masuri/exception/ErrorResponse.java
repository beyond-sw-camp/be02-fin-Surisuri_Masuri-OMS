package com.example.Surisuri_Masuri.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final Integer code;
    private final String message;
}
