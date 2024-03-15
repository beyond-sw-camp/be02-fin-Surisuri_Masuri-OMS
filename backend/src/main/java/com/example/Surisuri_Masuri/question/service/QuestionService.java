package com.example.Surisuri_Masuri.question.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.notice.model.entity.Notice;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public BaseResponse create(PostCreateQuestionReq postQuestionReq) {

        Question question = Question.builder()
                .category(postQuestionReq.getCategory())
                .title(postQuestionReq.getTitle())
                .content(postQuestionReq.getContent())
                .status(postQuestionReq.getStatus())
                .user(User.builder().idx(postQuestionReq.getUserIdx()).build())
                .build();
        questionRepository.save(question);

        PostCreateQuestionRes postCreateQuestionRes = PostCreateQuestionRes.builder()
                .category(postQuestionReq.getCategory())
                .title(postQuestionReq.getTitle())
                .content(postQuestionReq.getContent())
                .userIdx(postQuestionReq.getUserIdx())
                .build();

        return BaseResponse.successResponse("문의사항 작성 성공", postCreateQuestionRes);
    }


    public BaseResponse list(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        List<Question> questionList = questionRepository.findAll();

            List<GetListQuestionRes> getListQuestionResList = new ArrayList<>();
            for (Question question : questionList) {
                GetListQuestionRes getListQuestionRes = GetListQuestionRes.builder()
                        .category(question.getCategory())
                        .title(question.getTitle())
                        .content(question.getContent())
                        .build();

                getListQuestionResList.add(getListQuestionRes);
            }

            return BaseResponse.successResponse("문의사항 불러오기 성공", getListQuestionResList);

    }


    public BaseResponse update(PatchUpdateQuestionReq patchUpdateQuestionReq) {
        Optional<Question> result = questionRepository.findById(patchUpdateQuestionReq.getIdx());

        if (result.isPresent()) {
            Question question = result.get();
            question.update(patchUpdateQuestionReq);
            questionRepository.save(question);

            return BaseResponse.successResponse("문의사항 수정 성공",patchUpdateQuestionReq);
        } else {
            return BaseResponse.successResponse("문의사항 수정 실패",null);
        }
    }


    public BaseResponse delete(Integer idx) {
        Optional<Question> result = questionRepository.findById(idx);

        if(result.isPresent()) {
            Question question = result.get();
            questionRepository.delete(question);
            return BaseResponse.successResponse("문의사항 삭제 성공",null);
        }
        return BaseResponse.successResponse("삭제할 문의사항이 존재하지 않습니다", null);

    }


    public BaseResponse answer(QuestionAnswerReq req) {
        Optional<Question> questionResult = questionRepository.findById(req.getQuestionIdx());

        if (questionResult.isPresent()) {
            Optional<Answer> answerResult = answerRepository.findByQuestionIdx(req.getQuestionIdx());

            if (!answerResult.isPresent()) {
                answerRepository.save(Answer.builder()
                        .manager(Manager.builder().idx(req.getManagerIdx()).build())
                        .question(Question.builder().idx(req.getQuestionIdx()).build())
                        .answerContent(req.getAnswerContent())
                        .build());

                Question question = questionResult.get();
                question.setStatus(true);
                questionRepository.save(question);

                return BaseResponse.successResponse("요청 성공", null);
            } else {
                Answer answer = answerResult.get();

                if (answer.getManager().getIdx() != req.getManagerIdx())
                    answer.setManager(Manager.builder().idx(req.getManagerIdx()).build());
                if (answer.getAnswerContent() != null)
                    answer.setAnswerContent(req.getAnswerContent());

                answerRepository.save(answer);

                Question question = questionResult.get();
                question.setStatus(true);
                questionRepository.save(question);

                return BaseResponse.successResponse("수정 요청 성공", null);
            }
        }
        return BaseResponse.failResponse(444, "질문이 존재하지 않습니다");
    }

}

