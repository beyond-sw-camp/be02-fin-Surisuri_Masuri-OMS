package com.example.Surisuri_Masuri.member.Model.ResDtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ManagerSignUpRes {

    private String token;

    private String managerId;

    private String managerName;

    private String department;

}
