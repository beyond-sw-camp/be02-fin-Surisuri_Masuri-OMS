package com.example.Surisuri_Masuri.question.repository;

import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.question.model.entity.Answer;
import com.example.Surisuri_Masuri.question.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    public Optional<Answer> findByQuestionIdx(Integer questionIdx);
}
