package com.example.Surisuri_Masuri.member.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Builder
@Data
public class ManagerSignUpReq {

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어와 숫자만 가능합니다.")
    private String managerId;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$")
    private String managerPassword;

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String managerEmail;

    @Pattern(regexp = "^[가-힣]*$", message = "한글만 가능합니다.")
    private String managerName;

    @Pattern(regexp = "^(\\d{2,3}-?\\d{3,4}-?\\d{4}|\\d{10,11})$", message = "올바른 전화번호 형식이어야 합니다.")
    private String managerPhoneNo;

    @Pattern(regexp = "^[a-zA-Z]*$", message = "영어와 한글만 가능합니다.")
    private String department;

}
