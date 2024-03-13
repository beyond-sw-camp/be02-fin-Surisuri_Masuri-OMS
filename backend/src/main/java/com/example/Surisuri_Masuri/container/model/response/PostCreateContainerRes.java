package com.example.Surisuri_Masuri.container.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateContainerRes {

    private String containerAddr;
    private Integer containerComplexity;
    private String containerName;
    private String containerManager;
    private String containerPhoneNo;

}
