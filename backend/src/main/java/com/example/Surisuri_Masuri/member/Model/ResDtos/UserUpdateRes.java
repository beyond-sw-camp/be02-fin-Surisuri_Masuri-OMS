package com.example.Surisuri_Masuri.member.Model.ResDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRes {
    private String storeAddr;
    private String storePhoneNo;
    private String userPhoneNo;
    private String userPassword;
}
