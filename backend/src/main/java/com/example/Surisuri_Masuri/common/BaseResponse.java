package com.example.Surisuri_Masuri.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private T result;

    public static <T> BaseResponse<T> successResponse(String message, T result) {
        return new BaseResponse<>(true, 200, message, result);
    }

    public static <T> BaseResponse<T> failResponse(Integer code, String message) {
        return new BaseResponse<>(false, code, message, null);
    }
}