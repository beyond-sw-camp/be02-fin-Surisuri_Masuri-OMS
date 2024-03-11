package com.example.Surisuri_Masuri.email.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendEmailReq {
    private String email;
    private String authority;
    private String accessToken;
}
