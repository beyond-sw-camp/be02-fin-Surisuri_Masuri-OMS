package com.example.Surisuri_Masuri.member.Model.ResDtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRes {
    private final String jwtToken;
}
