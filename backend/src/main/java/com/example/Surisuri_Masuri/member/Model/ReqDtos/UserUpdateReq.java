package com.example.Surisuri_Masuri.member.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@Builder
public class UserUpdateReq {
    private String storeAddr;

    @Pattern(regexp = "^(\\d{2,3}-?\\d{3,4}-?\\d{4}|\\d{10,11})$", message = "올바른 전화번호 형식이어야 합니다.")
    private String storePhoneNo;

    @Pattern(regexp = "^(\\d{2,3}-?\\d{3,4}-?\\d{4}|\\d{10,11})$", message = "올바른 전화번호 형식이어야 합니다.")
    private String userPhoneNo;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$")
    private String userPassword;
}
