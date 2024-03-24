package com.example.Surisuri_Masuri.question.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.exception.EntityException.ContainerException;
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
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    public BaseResponse create(User user, PostCreateQuestionReq postQuestionReq) {
        Optional<User> userResult = userRepository.findByUserEmail(user.getUserEmail());
        User foundUser = userResult.get();


        questionRepository.save(Question.builder()
                .category(postQuestionReq.getCategory())
                .title(postQuestionReq.getTitle())
                .content(postQuestionReq.getContent())
                .status(postQuestionReq.getStatus())
                .user(foundUser)
                .build());

        PostCreateQuestionRes postCreateQuestionRes = PostCreateQuestionRes.builder()
                .category(postQuestionReq.getCategory())
                .title(postQuestionReq.getTitle())
                .content(postQuestionReq.getContent())
                .userIdx(foundUser.getIdx())
                .build();

        return BaseResponse.successResponse("문의사항 작성 성공", null);

    }


    public BaseResponse list(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        List<Question> questionList = questionRepository.findAll();

        List<GetListQuestionRes> getListQuestionResList = new ArrayList<>();
        for (Question question : questionList) {
            GetListQuestionRes getListQuestionRes = GetListQuestionRes.builder()
                    .questionIdx(question.getIdx())
                    .category(question.getCategory())
                    .title(question.getTitle())
                    .content(question.getContent())
                    .userIdx(question.getUser().getIdx())
                    .answerContent(question.getAnswer().getAnswerContent())
                    .build();

            getListQuestionResList.add(getListQuestionRes);
        }

        return BaseResponse.successResponse("문의사항 불러오기 성공", getListQuestionResList);

    }


    public BaseResponse update(User user, PatchUpdateQuestionReq patchUpdateQuestionReq) {
        Optional<Question> result = questionRepository.findById(patchUpdateQuestionReq.getIdx());

        if (result.isPresent()) {
            if (result.get().getUser().getIdx().equals(user.getIdx())) {
                Question question = result.get();
                question.update(patchUpdateQuestionReq);
                questionRepository.save(question);
            }
            // return BaseResponse.failResponse(444, "본인이 작성한 문의사항이 아닙니다");
            return BaseResponse.successResponse("문의사항 수정 성공", patchUpdateQuestionReq);
        }
        else throw new ContainerException(ErrorCode.QuestionUpdate_005,
                    String.format("Question Idx [ %s ] doesn't has Question.", patchUpdateQuestionReq.getIdx()));

    }


    public BaseResponse delete(User user, Integer idx) {
        Optional<Question> result = questionRepository.findById(idx);

        if (result.isPresent()) {
            if (result.get().getUser().getIdx().equals(user.getIdx())) {
                Question question = result.get();
                questionRepository.delete(question);
            }
            return BaseResponse.successResponse("문의사항 삭제 성공", null);
        }

        else {
            throw new ContainerException(ErrorCode.QuestionDelete_002,
                    String.format("Question Idx [ %s ] doesn't has Question.", idx));
        }
    }

    public BaseResponse answer(Manager manager, QuestionAnswerReq req) {
        Optional<Manager> managerResult = managerRepository.findByManagerId(manager.getManagerId());
        manager = managerResult.get();
        Optional<Question> questionResult = questionRepository.findById(req.getQuestionIdx());
        Question question = questionResult.get();

        if (questionResult.isPresent()) {
            Optional<Answer> answerResult = answerRepository.findByQuestionIdx(req.getQuestionIdx());

            if (!answerResult.isPresent()) {
                answerRepository.save(Answer.builder()
                        .manager(manager)
                        .question(question)
                        .answerContent(req.getAnswerContent())
                        .build());

                question.setStatus(true);
                questionRepository.save(question);

                return BaseResponse.successResponse("요청 성공", null);
            } else {
                Answer answer = answerResult.get();

                if (!answer.getManager().getManagerId().equals(manager.getManagerId()))
                    answer.setManager(manager);
                if (answer.getAnswerContent() != null)
                    answer.setAnswerContent(req.getAnswerContent());

                answerRepository.save(answer);

                return BaseResponse.successResponse("수정 요청 성공", null);
            }
        }
        else {
            throw new ContainerException(ErrorCode.QuestionAnswer_003,
                    String.format("Question Idx [ %s ] doesn't has Question.", req.getQuestionIdx()));
        }
    }
}

