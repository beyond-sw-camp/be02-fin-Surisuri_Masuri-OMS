package com.example.Surisuri_Masuri.question.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.exception.EntityException.ContainerException;
import com.example.Surisuri_Masuri.exception.EntityException.UserException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Repository.ManagerRepository;
import com.example.Surisuri_Masuri.member.Repository.UserRepository;
import com.example.Surisuri_Masuri.question.model.entity.Answer;
import com.example.Surisuri_Masuri.question.model.entity.Question;
import com.example.Surisuri_Masuri.question.model.request.PatchUpdateQuestionReq;
import com.example.Surisuri_Masuri.question.model.request.PostCreateQuestionReq;
import com.example.Surisuri_Masuri.question.model.request.QuestionAnswerReq;
import com.example.Surisuri_Masuri.question.model.response.GetListQuestionRes;
import com.example.Surisuri_Masuri.question.model.response.PostCreateQuestionRes;
import com.example.Surisuri_Masuri.question.repository.AnswerRepository;
import com.example.Surisuri_Masuri.question.repository.QuestionRepository;
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
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    public BaseResponse create(String token, PostCreateQuestionReq postQuestionReq) {

        token = JwtUtils.replaceToken(token);

        String userId = JwtUtils.getUserEmail(token, secretKey);

        Optional<User> user = userRepository.findByUserEmail(userId);

        if (user.isPresent()) {
            questionRepository.save(Question.builder()
                    .category(postQuestionReq.getCategory())
                    .title(postQuestionReq.getTitle())
                    .content(postQuestionReq.getContent())
                    .status(false)
                    .user(user.get())
                    .build());

            PostCreateQuestionRes postCreateQuestionRes = PostCreateQuestionRes.builder()
                    .category(postQuestionReq.getCategory())
                    .title(postQuestionReq.getTitle())
                    .content(postQuestionReq.getContent())
                    .userIdx(user.get().getIdx())
                    .build();

            return BaseResponse.successResponse("문의사항 작성을 성공했습니다.", null);
        }   else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }

    public BaseResponse<List<GetListQuestionRes>> list(String token,Integer page, Integer size) {

        token = JwtUtils.replaceToken(token);

        String userId = JwtUtils.getUserEmail(token, secretKey);
        String managerId = JwtUtils.getManagerId(token,secretKey);

        Optional<User> user = userRepository.findByUserEmail(userId);
        Optional<Manager> manager = managerRepository.findByManagerId(managerId);

        if (user.isPresent()) {
            Pageable pageable = PageRequest.of(page - 1, size);

            Page<Question> result = questionRepository.findList(pageable);

            List<GetListQuestionRes> getListQuestionResList = new ArrayList<>();

            for (Question question : result.getContent()) {

                GetListQuestionRes getListQuestionRes = GetListQuestionRes.builder()
                        .questionIdx(question.getIdx())
                        .category(question.getCategory())
                        .title(question.getTitle())
                        .content(question.getContent())
                        .userIdx(question.getUser().getIdx())
                        .answerContent(Optional.ofNullable(question.getAnswer())
                                .map(Answer::getAnswerContent)
                                .orElse(null))  // answerContent가 null인 경우 null로 설정
                        .build();
                getListQuestionResList.add(getListQuestionRes);
            }
            return BaseResponse.successResponse("문의사항 목록 조회를 성공했습니다.", getListQuestionResList);
        }

        else if (manager.isPresent()) {
            Pageable pageable = PageRequest.of(page - 1, size);

            Page<Question> result = questionRepository.findList(pageable);

            List<GetListQuestionRes> getListQuestionResList = new ArrayList<>();

            for (Question question : result.getContent()) {

                GetListQuestionRes getListQuestionRes = GetListQuestionRes.builder()
                        .questionIdx(question.getIdx())
                        .category(question.getCategory())
                        .title(question.getTitle())
                        .content(question.getContent())
                        .userIdx(question.getUser().getIdx())
                        .answerContent(Optional.ofNullable(question.getAnswer())
                                .map(Answer::getAnswerContent)
                                .orElse(null))  // answerContent가 null인 경우 null로 설정
                        .build();
                getListQuestionResList.add(getListQuestionRes);
            }
            return BaseResponse.successResponse("문의사항 목록 조회를 성공했습니다.", getListQuestionResList);
        }

        else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }

    public BaseResponse update(String token, PatchUpdateQuestionReq patchUpdateQuestionReq) {

        token = JwtUtils.replaceToken(token);

        String userId = JwtUtils.getUserEmail(token, secretKey);

        Optional<User> user = userRepository.findByUserEmail(userId);

        if (user.isPresent()) {
            Optional<Question> result = questionRepository.findById(patchUpdateQuestionReq.getIdx());

            if (result.isPresent()) {
                if (result.get().getUser().getIdx().equals(user.get().getIdx())) {
                    Question question = result.get();
                    question.update(patchUpdateQuestionReq);
                    questionRepository.save(question);
                }
                return BaseResponse.successResponse("문의사항 수정을 성공했습니다.", patchUpdateQuestionReq);
            } else throw new ContainerException(ErrorCode.QuestionUpdate_005,
                    String.format("수정할 문의사항 Idx [ %s ] 이/가 존재하지 않습니다.", patchUpdateQuestionReq.getIdx()));

        }  else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }

    public BaseResponse delete(String token, Integer idx) {
        token = JwtUtils.replaceToken(token);

        String userId = JwtUtils.getUserEmail(token, secretKey);

        Optional<User> user = userRepository.findByUserEmail(userId);

        if (user.isPresent()) {
            Optional<Question> result = questionRepository.findById(idx);

            if (result.isPresent()) {
                if (result.get().getUser().getIdx().equals(user.get().getIdx())) {
                    Question question = result.get();
                    questionRepository.delete(question);
                }
                return BaseResponse.successResponse("문의사항 삭제를 성공했습니다.", null);
            } else {
                throw new ContainerException(ErrorCode.QuestionDelete_002,
                        String.format("삭제할 공지사항 Idx [ %s ] 이/가 존재하지 않습니다.", idx));
            }
        } else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }

    public BaseResponse answer(String token, QuestionAnswerReq req) {
        token = JwtUtils.replaceToken(token);

        String managerId = JwtUtils.getManagerInfo(token, secretKey);

        Optional<Manager> manager = managerRepository.findByManagerId(managerId);

        Optional<Question> questionResult = questionRepository.findById(req.getQuestionIdx());
        Question question = questionResult.get();

        if (questionResult.isPresent()) {
            Optional<Answer> answerResult = answerRepository.findByQuestionIdx(req.getQuestionIdx());

            if (!answerResult.isPresent()) {
                answerRepository.save(Answer.builder()
                        .manager(manager.get())
                        .answerContent(req.getAnswerContent())
                        .question(question)
                        .build());

                question.setStatus(true);
                questionRepository.save(question);

                return BaseResponse.successResponse("요청 성공했습니다.", null);
            } else {
                Answer answer = answerResult.get();

                if (!answer.getManager().getManagerId().equals(manager.get().getManagerId()))
                    answer.setManager(manager.get());
                if (answer.getAnswerContent() != null)
                    answer.setAnswerContent(req.getAnswerContent());

                answerRepository.save(answer);

                return BaseResponse.successResponse("문의사항에 대한 답변이 수정 되었습니다.", null);
            }
        }
        else {
            throw new ContainerException(ErrorCode.QuestionAnswer_003,
                    String.format("답변을 작성할 문의사항 Idx [ %s ] 이/가 존재하지 않습니다.", req.getQuestionIdx()));
        }
    }
}