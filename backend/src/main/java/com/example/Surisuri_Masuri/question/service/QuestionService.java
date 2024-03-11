package com.example.Surisuri_Masuri.question.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.question.model.entity.Question;
import com.example.Surisuri_Masuri.question.model.request.PatchUpdateQuestionReq;
import com.example.Surisuri_Masuri.question.model.request.PostCreateQuestionReq;
import com.example.Surisuri_Masuri.question.model.response.GetListQuestionRes;
import com.example.Surisuri_Masuri.question.model.response.PostCreateQuestionRes;
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

    public BaseResponse create(PostCreateQuestionReq postQuestionReq) {

        Question question = Question.builder()
                .category(postQuestionReq.getCategory())
                .title(postQuestionReq.getTitle())
                .content(postQuestionReq.getContent())
                .status(postQuestionReq.getStatus())
                .build();
        questionRepository.save(question);

        PostCreateQuestionRes postCreateQuestionRes = PostCreateQuestionRes.builder()
                .category(postQuestionReq.getCategory())
                .title(postQuestionReq.getTitle())
                .content(postQuestionReq.getContent())
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
        Optional<Question> result = questionRepository.findById(patchUpdateQuestionReq.getQuestionIdx());

        if (result.isPresent()) {
            Question question = result.get();
            question.update(patchUpdateQuestionReq);
            questionRepository.save(question);

            return BaseResponse.successResponse("문의사항 수정 성공",patchUpdateQuestionReq);
        } else {
            return BaseResponse.successResponse("문의사항 수정 실패",null);
        }
    }


    public BaseResponse delete(Integer questionIdx) {
        Optional<Question> result = questionRepository.findById(questionIdx);

        if(result.isPresent()) {
            Question question = result.get();
            questionRepository.delete(question);
            return BaseResponse.successResponse("문의사항 삭제 성공",null);
        }
        return BaseResponse.successResponse("삭제할 문의사항이 존재하지 않습니다", null);

    }


    public void answer() {
    }

}

