package com.example.Surisuri_Masuri.member.Model.ResDtos;

import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LoginRes {
    private String jwtToken;
    private String refreshToken;
    private List<DiscardedProduct> discardedProduct;
}
