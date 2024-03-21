package com.example.Surisuri_Masuri.member.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@Builder
public class UserUpdateReq {
    private String storeAddr;

    @Pattern(regexp = "^(\\d{2,3}-?\\d{3,4}-?\\d{4}|\\d{10,11}|)$", message = "올바른 전화번호 형식이어야 합니다.")
    private String storePhoneNo;

    @Pattern(regexp = "^(\\d{2,3}-?\\d{3,4}-?\\d{4}|\\d{10,11}|)$", message = "올바른 전화번호 형식이어야 합니다.")
    private String userPhoneNo;

    private String userPassword;

    public boolean isUserPasswordValid() {
        // 비밀번호가 null 또는 공백인 경우는 유효하다고 처리
        return userPassword.trim().isEmpty() || userPassword.matches("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9])[\\s\\S]{8,15}$");
    }

}
