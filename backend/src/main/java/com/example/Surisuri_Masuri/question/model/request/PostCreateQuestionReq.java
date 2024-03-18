package com.example.Surisuri_Masuri.question.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateQuestionReq {

    private String category;
    private String title;
    private String content;
    private Boolean status;
    private Long userIdx;

}
