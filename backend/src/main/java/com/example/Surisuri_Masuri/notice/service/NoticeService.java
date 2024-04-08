package com.example.Surisuri_Masuri.notice.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.exception.EntityException.ContainerException;
import com.example.Surisuri_Masuri.exception.EntityException.StoreException;
import com.example.Surisuri_Masuri.exception.EntityException.UserException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Repository.ManagerRepository;
import com.example.Surisuri_Masuri.member.Repository.UserRepository;
import com.example.Surisuri_Masuri.notice.model.entity.Notice;
import com.example.Surisuri_Masuri.notice.model.request.PatchUpdateNoticeReq;
import com.example.Surisuri_Masuri.notice.model.request.PostCreateNoticeReq;
import com.example.Surisuri_Masuri.notice.model.response.GetListNoticeRes;
import com.example.Surisuri_Masuri.notice.model.response.PostCreateNoticeRes;
import com.example.Surisuri_Masuri.notice.repository.NoticeRepository;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import com.example.Surisuri_Masuri.store.Repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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

    private final ManagerRepository managerRepository;

    private final UserRepository userRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public BaseResponse create(String token,PostCreateNoticeReq postCreateNoticeReq) {

        token = JwtUtils.replaceToken(token);

        String managerId = JwtUtils.getManagerInfo(token, secretKey);

        Optional<Manager> manager = managerRepository.findByManagerId(managerId);

        if (manager.isPresent()) {
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

            return BaseResponse.successResponse("공지사항 작성을 성공했습니다.", postCreateNoticeRes);
        } else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }


    public BaseResponse list(String token,Integer page, Integer size) {
        token = JwtUtils.replaceToken(token);

        String managerId = JwtUtils.getManagerInfo(token, secretKey);
        String userId = JwtUtils.getUserEmail(token,secretKey);

        Optional<Manager> manager = managerRepository.findByManagerId(managerId);
        Optional<User> user = userRepository.findByUserEmail(userId);


        if (manager.isPresent()) {

            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Notice> noticeList = noticeRepository.findList(pageable);

            List<GetListNoticeRes> getListNoticeResList = new ArrayList<>();
            for (Notice notice : noticeList) {
                GetListNoticeRes getListNoticeRes = GetListNoticeRes.builder()
                        .noticeIdx(notice.getNoticeIdx())
                        .category(notice.getCategory())
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .build();

                getListNoticeResList.add(getListNoticeRes);
            }
            return BaseResponse.successResponse("공지사항 조회를 성공했습니다.", getListNoticeResList);
        }

        else if (user.isPresent()) {

            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Notice> noticeList = noticeRepository.findList(pageable);

            List<GetListNoticeRes> getListNoticeResList = new ArrayList<>();
            for (Notice notice : noticeList) {
                GetListNoticeRes getListNoticeRes = GetListNoticeRes.builder()
                        .noticeIdx(notice.getNoticeIdx())
                        .category(notice.getCategory())
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .build();

                getListNoticeResList.add(getListNoticeRes);
            }
            return BaseResponse.successResponse("공지사항 조회를 성공했습니다.", getListNoticeResList);
        }

        else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 유저입니다"));
        }
    }


    public BaseResponse update(String token,PatchUpdateNoticeReq patchUpdateNoticeReq) {

        token = JwtUtils.replaceToken(token);

        String managerId = JwtUtils.getManagerInfo(token, secretKey);

        Optional<Manager> manager = managerRepository.findByManagerId(managerId);

        if (manager.isPresent()) {
            Optional<Notice> result = noticeRepository.findById(patchUpdateNoticeReq.getNoticeIdx());

            if (result.isPresent()) {
                Notice notice = result.get();
                notice.update(patchUpdateNoticeReq);
                noticeRepository.save(notice);

                return BaseResponse.successResponse("공지사항 수정을 성공했습니다.", null);
            } else {
                throw new ContainerException(ErrorCode.NoticeUpdate_002,
                        String.format("공지사항 Idx [ %s ] 이/가 존재하지 않습니다.", patchUpdateNoticeReq.getNoticeIdx()));
            }
        } else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }


    public BaseResponse delete(String token,Integer noticeIdx) {
        token = JwtUtils.replaceToken(token);

        String managerId = JwtUtils.getManagerInfo(token, secretKey);

        Optional<Manager> manager = managerRepository.findByManagerId(managerId);

        if (manager.isPresent()) {
            Optional<Notice> result = noticeRepository.findById(noticeIdx);

            if (result.isPresent()) {
                Notice notice = result.get();
                noticeRepository.delete(notice);
                return BaseResponse.successResponse("공지사항 삭제를 성공했습니다.", null);
            } else {
                throw new ContainerException(ErrorCode.NoticeDelete_002,
                        String.format("공지사항 Idx [ %s ] 이/가 존재하지 않습니다.", noticeIdx));
            }
        } else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }
}

