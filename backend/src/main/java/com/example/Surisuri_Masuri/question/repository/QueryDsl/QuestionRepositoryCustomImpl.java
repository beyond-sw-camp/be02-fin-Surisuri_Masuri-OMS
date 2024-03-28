package com.example.Surisuri_Masuri.question.repository.QueryDsl;

import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.model.QProduct;
import com.example.Surisuri_Masuri.question.model.entity.QQuestion;
import com.example.Surisuri_Masuri.question.model.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class QuestionRepositoryCustomImpl extends QuerydslRepositorySupport implements QuestionRepositoryCustom {
    public QuestionRepositoryCustomImpl() {
        super(Question.class);
    }
    @Override
    public Page<Question> findList(Pageable pageable) {
        QQuestion question= new QQuestion("question");

        List<Question> result = from(question)
                .distinct()
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }

}
