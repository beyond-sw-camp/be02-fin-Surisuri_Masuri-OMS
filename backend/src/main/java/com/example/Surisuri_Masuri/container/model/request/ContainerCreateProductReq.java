package com.example.Surisuri_Masuri.container.model.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerCreateProductReq {
    Integer containerIdx;
    Long productIdx;
    Long quantity;
    LocalDate expiredAt;
}
