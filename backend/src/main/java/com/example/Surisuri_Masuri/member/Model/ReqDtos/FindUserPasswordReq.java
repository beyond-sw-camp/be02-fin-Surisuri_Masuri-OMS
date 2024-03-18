package com.example.Surisuri_Masuri.member.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class FindUserPasswordReq {

    @Pattern(regexp = "^[가-힣]*$", message = "한글만 가능합니다.")
    private String userName;

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String userEmail;

}
