package com.example.Surisuri_Masuri.member.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserFindEmailReq {

    private String userName;

    private String userPhoneNo;

}
