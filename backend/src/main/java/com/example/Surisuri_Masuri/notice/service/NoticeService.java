package com.example.Surisuri_Masuri.notice.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.notice.model.entity.Notice;
import com.example.Surisuri_Masuri.notice.model.request.PatchUpdateNoticeReq;
import com.example.Surisuri_Masuri.notice.model.request.PostCreateNoticeReq;
import com.example.Surisuri_Masuri.notice.model.response.GetListNoticeRes;
import com.example.Surisuri_Masuri.notice.model.response.PostCreateNoticeRes;
import com.example.Surisuri_Masuri.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public BaseResponse create(PostCreateNoticeReq postCreateNoticeReq) {

        Notice notice = Notice.builder()
                .category(postCreateNoticeReq.getCategory())
                .title(postCreateNoticeReq.getTitle())
                .content(postCreateNoticeReq.getContent())
                .status(postCreateNoticeReq.getStatus())
                .build();
        noticeRepository.save(notice);

        PostCreateNoticeRes postCreateNoticeRes = PostCreateNoticeRes.builder()
                .category(postCreateNoticeReq.getCategory())
                .title(postCreateNoticeReq.getTitle())
                .content(postCreateNoticeReq.getContent())
                .build();

        return BaseResponse.successResponse("공지사항 작성 성공", postCreateNoticeRes);
    }


    public BaseResponse list(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        List<Notice> noticeList = noticeRepository.findAll();

            List<GetListNoticeRes> getListNoticeResList = new ArrayList<>();
            for (Notice notice : noticeList) {
                GetListNoticeRes getListNoticeRes = GetListNoticeRes.builder()
                        .category(notice.getCategory())
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .build();

                getListNoticeResList.add(getListNoticeRes);
            }

            return BaseResponse.successResponse("공지사항 조회 성공",getListNoticeResList);

    }


    public BaseResponse update(PatchUpdateNoticeReq patchUpdateNoticeReq) {
        Optional<Notice> result = noticeRepository.findById(patchUpdateNoticeReq.getNoticeIdx());

        if (result.isPresent()) {
            Notice notice = result.get();
            notice.update(patchUpdateNoticeReq);
            noticeRepository.save(notice);

            return BaseResponse.successResponse("공지사항 수정 성공", null);
        } else {
            return BaseResponse.successResponse("공지사항 수정 실패",null);
        }
    }


    public BaseResponse delete(Integer noticeIdx) {
        Optional<Notice> result = noticeRepository.findById(noticeIdx);

        if(result.isPresent()) {
            Notice notice = result.get();
            noticeRepository.delete(notice);
            return BaseResponse.successResponse("공지사항 삭제 성공",null);
        } else {
            return BaseResponse.successResponse("삭제할 공지사항이 존재하지 않습니다",null);
        }

    }

}

