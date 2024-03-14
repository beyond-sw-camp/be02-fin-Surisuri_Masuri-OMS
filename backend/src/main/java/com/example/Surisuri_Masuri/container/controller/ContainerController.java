package com.example.Surisuri_Masuri.container.controller;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.container.model.request.PostCreateContainerReq;
import com.example.Surisuri_Masuri.container.service.ContainerService;
import com.example.Surisuri_Masuri.notice.model.request.PostCreateNoticeReq;
import com.example.Surisuri_Masuri.question.model.request.PatchUpdateQuestionReq;
import com.example.Surisuri_Masuri.question.model.request.PostCreateQuestionReq;
import com.example.Surisuri_Masuri.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/container")
@CrossOrigin("*")
public class ContainerController {

    private final ContainerService containerService;


    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(PostCreateContainerReq postCreateContainerReq) {
        BaseResponse baseResponse = containerService.create(postCreateContainerReq);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Integer page, Integer size) {
        BaseResponse baseResponse = containerService.list(page, size);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/singlestock")
    public ResponseEntity singleStockProduct(Integer idx) {
        BaseResponse baseResponse = containerService.singleStockProduct(idx);
        return ResponseEntity.ok().body(baseResponse);
    }

}
