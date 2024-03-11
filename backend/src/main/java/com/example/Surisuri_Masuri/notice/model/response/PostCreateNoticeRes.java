package com.example.Surisuri_Masuri.notice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateNoticeRes {

    private String category;
    private String title;
    private String content;
    private Boolean status;

}
