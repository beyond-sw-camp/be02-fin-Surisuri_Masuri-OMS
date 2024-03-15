package com.example.Surisuri_Masuri.container.model.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContainerStockDto {
    private String containerName;
    private String productName;
    private Long productQuantity;
    private LocalDate expiredAt;
}