package com.example.Surisuri_Masuri.member.Model.ReqDtos;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@Builder
public class LoginReq {

    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "아이디는 영문자와 숫자를 포함한 4~12글자여야 합니다.")
    String id;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$",message = "잘못된 비밀번호 형식입니다.")
    String password;
}
