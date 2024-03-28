package com.example.Surisuri_Masuri.member.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

// 가맹점 회원가입 Dto
@Builder
@Data
public class UserSignUpReq {

    @Pattern(regexp = "^[가-힣]*$", message = "한글만 입력이 가능합니다.")
    private String userName;

    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "올바른 이메일 형식(test@example.com)으로 입력해야 합니다.")
    private String userEmail;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$", message = "비밀번호는 대문자를 포함한 8~15자의 영문, 숫자, 특수문자(!@#$%^&*()_+-=[])만 가능합니다.")
    private String userPassword;

    @Pattern(regexp = "^(\\d{2,3}-?\\d{3,4}-?\\d{4}|\\d{10,11})$", message = "올바른 전화번호 형식이어야 합니다.")
    private String userPhoneNo;

    private String storeUuid;

    private String storeAddr;

    @Pattern(regexp = "^(\\d{2,3}-?\\d{3,4}-?\\d{4}|\\d{10,11})$", message = "올바른 전화번호 형식이어야 합니다.")
    private String storePhoneNo;

}
