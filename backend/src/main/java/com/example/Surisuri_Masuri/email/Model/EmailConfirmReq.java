package com.example.Surisuri_Masuri.email.Model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class EmailConfirmReq {
    private String email;
    private String uuid;
    private String authority;
}
