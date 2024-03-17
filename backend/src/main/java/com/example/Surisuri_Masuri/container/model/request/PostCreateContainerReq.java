package com.example.Surisuri_Masuri.container.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateContainerReq {

    private String containerAddr;
    private Integer containerComplexity;
    private String containerName;

    @Pattern(regexp = "^[가-힣]*$", message = "한글만 가능합니다.")
    private String containerManager;

    @Pattern(regexp = "^(\\d{2,3}-?\\d{3,4}-?\\d{4}|\\d{10,11})$", message = "올바른 전화번호 형식이어야 합니다.")
    private String containerPhoneNo;
}
