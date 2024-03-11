package com.example.Surisuri_Masuri.member.Model.ResDtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserSignUpRes {
    private String userName;

    private String userEmail;

    private String userPhoneNo;

    private String storeUuid;

    private String storeAddr;

    private String storePhoneNo;

    private Boolean status;
}
