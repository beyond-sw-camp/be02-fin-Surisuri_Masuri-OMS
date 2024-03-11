package com.example.Surisuri_Masuri.member.Model.ReqDtos;

import com.example.Surisuri_Masuri.common.Address;
import lombok.Builder;
import lombok.Data;

// 가맹점 회원가입 Dto
@Builder
@Data
public class UserSignUpReq {

    private String userName;

    private String userEmail;

    private String userPassword;

    private String userPhoneNo;

    private String storeUuid;

    private String storeAddr;

    private String storePhoneNo;

}
