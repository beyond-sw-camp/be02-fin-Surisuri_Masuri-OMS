package com.example.Surisuri_Masuri.question.controller;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.question.model.request.PatchUpdateQuestionReq;
import com.example.Surisuri_Masuri.question.model.request.PostCreateQuestionReq;
import com.example.Surisuri_Masuri.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    private final QuestionService questionService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(PostCreateQuestionReq postCreateQuestionReq) {
        BaseResponse baseResponse = questionService.create(postCreateQuestionReq);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Integer page, Integer size) {
        BaseResponse baseResponse = questionService.list(page,size);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(PatchUpdateQuestionReq patchUpdateQuestionReq) {
        BaseResponse baseResponse = questionService.update(patchUpdateQuestionReq);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Integer idx) {
        BaseResponse baseResponse = questionService.delete(idx);
        return ResponseEntity.ok().body(baseResponse);
    }

    // TODO: 2024-03-10 관리자가 답변을 작성하는 기능 추가해야함
    @RequestMapping(method = RequestMethod.POST, value = "/answer")
    public ResponseEntity answer() {
        return ResponseEntity.ok().body("");
    }

}
