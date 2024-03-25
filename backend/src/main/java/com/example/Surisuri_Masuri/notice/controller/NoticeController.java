package com.example.Surisuri_Masuri.notice.controller;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.notice.model.request.PatchUpdateNoticeReq;
import com.example.Surisuri_Masuri.notice.model.request.PostCreateNoticeReq;
import com.example.Surisuri_Masuri.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
@CrossOrigin("*")
public class NoticeController {

    private final NoticeService noticeService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@RequestBody PostCreateNoticeReq postCreateNoticeReq) {
        BaseResponse baseResponse = noticeService.create(postCreateNoticeReq);
        return ResponseEntity.ok().body(baseResponse);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Integer page, Integer size) {
        BaseResponse baseResponse = noticeService.list(page,size);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(PatchUpdateNoticeReq patchUpdateNoticeReq) {
        BaseResponse baseResponse = noticeService.update(patchUpdateNoticeReq);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Integer noticeIdx) {
        BaseResponse baseResponse = noticeService.delete(noticeIdx);
        return ResponseEntity.ok().body(baseResponse);
    }

}
