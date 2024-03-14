package com.example.Surisuri_Masuri.storeStock.Model.ResDtos;

import com.example.Surisuri_Masuri.product.model.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreStockSearchRes {

   private StoreStockDto productName;
   private Long stockQuantity;
   private String storeAddr;

}
