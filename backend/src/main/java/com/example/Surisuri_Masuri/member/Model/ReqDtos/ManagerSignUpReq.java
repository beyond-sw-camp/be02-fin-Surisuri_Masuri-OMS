package com.example.Surisuri_Masuri.member.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ManagerSignUpReq {

    private String managerId;

    private String managerPassword;

    private String managerEmail;

    private String managerName;

    private String managerPhoneNo;

    private String department;

}
