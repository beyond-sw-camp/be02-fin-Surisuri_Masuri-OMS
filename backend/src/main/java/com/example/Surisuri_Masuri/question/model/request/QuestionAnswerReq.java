package com.example.Surisuri_Masuri.question.model.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class QuestionAnswerReq {
    Integer questionIdx;
    Long managerIdx;
    String answerContent;
}
