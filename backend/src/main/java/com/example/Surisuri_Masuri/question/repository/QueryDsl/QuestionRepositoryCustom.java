package com.example.Surisuri_Masuri.question.repository.QueryDsl;

import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.question.model.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {
    public Page<Question> findList(Pageable pageable);
}
