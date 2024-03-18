package com.example.Surisuri_Masuri.member.Model.ReqDtos;

import lombok.*;

import javax.validation.constraints.Pattern;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserFindEmailReq {

    @Pattern(regexp = "^[가-힣]*$", message = "한글만 가능합니다.")
    String userName;

    @Pattern(regexp = "^(\\d{2,3}-?\\d{3,4}-?\\d{4}|\\d{10,11})$", message = "올바른 전화번호 형식이어야 합니다.")
    String userPhoneNo;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }
}
