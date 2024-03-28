package com.example.Surisuri_Masuri.question.repository;

import com.example.Surisuri_Masuri.question.model.entity.Question;
import com.example.Surisuri_Masuri.question.repository.QueryDsl.QuestionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer>, QuestionRepositoryCustom {

//    Integer deleteByQuestionIdxAndUser_userIdx(Integer questionIdx, Integer userIdx); 나중에 user 추가

}
