package com.example.Surisuri_Masuri.member.Model.ReqDtos;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserFindEmailReq {
    String userName;

    String userPhoneNo;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }
}
