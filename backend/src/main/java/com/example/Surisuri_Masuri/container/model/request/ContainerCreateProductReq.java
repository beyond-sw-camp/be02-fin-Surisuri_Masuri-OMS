package com.example.Surisuri_Masuri.container.model.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ContainerCreateProductReq {
    Integer containerIdx;
    Long productIdx;
    Long quantity;
    LocalDate expireAt;
}
