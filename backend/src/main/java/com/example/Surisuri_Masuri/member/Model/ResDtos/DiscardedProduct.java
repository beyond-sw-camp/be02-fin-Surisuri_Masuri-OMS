package com.example.Surisuri_Masuri.member.Model.ResDtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DiscardedProduct {
    private String productName;
    private LocalDate expiredDate;
}
