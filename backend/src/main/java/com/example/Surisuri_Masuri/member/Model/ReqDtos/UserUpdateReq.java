package com.example.Surisuri_Masuri.member.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateReq {
    private String storeAddr;
    private String storePhoneNo;
    private String userPhoneNo;
    private String userPassword;
}
