package com.example.Surisuri_Masuri.question.controller;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.question.model.request.QuestionAnswerReq;
import com.example.Surisuri_Masuri.question.model.request.PatchUpdateQuestionReq;
import com.example.Surisuri_Masuri.question.model.request.PostCreateQuestionReq;
import com.example.Surisuri_Masuri.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    private final QuestionService questionService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@RequestHeader(value = "AccessToken") String token, @RequestBody PostCreateQuestionReq postCreateQuestionReq) {
        BaseResponse baseResponse = questionService.create(token, postCreateQuestionReq);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(@RequestHeader(value = "AccessToken") String token,Integer page, Integer size) {
        BaseResponse baseResponse = questionService.list(token,page,size);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(@RequestHeader(value = "AccessToken") String token, @RequestBody PatchUpdateQuestionReq patchUpdateQuestionReq) {
        BaseResponse baseResponse = questionService.update(token, patchUpdateQuestionReq);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(@RequestHeader(value = "AccessToken") String token, Integer idx) {
        BaseResponse baseResponse = questionService.delete(token, idx);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/answer")
    public ResponseEntity answer(@RequestHeader(value = "AccessToken") String token, @RequestBody QuestionAnswerReq req) {

        return ResponseEntity.ok().body(questionService.answer(token, req));
    }

}
