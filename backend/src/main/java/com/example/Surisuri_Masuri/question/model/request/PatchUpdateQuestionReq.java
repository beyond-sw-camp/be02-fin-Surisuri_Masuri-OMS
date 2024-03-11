package com.example.Surisuri_Masuri.question.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchUpdateQuestionReq {

    private Integer idx;
    private String category;
    private String title;
    private String content;
    private Boolean status;

}
