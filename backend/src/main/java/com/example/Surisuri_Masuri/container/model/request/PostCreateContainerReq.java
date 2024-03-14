package com.example.Surisuri_Masuri.container.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateContainerReq {

    private String containerAddr;
    private Integer containerComplexity;
    private String containerName;
    private String containerManager;
    private String containerPhoneNo;
}
