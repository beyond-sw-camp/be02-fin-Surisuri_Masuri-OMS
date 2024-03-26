package com.example.Surisuri_Masuri.notice.repository.QueryDsl;

import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.notice.model.entity.Notice;
import com.example.Surisuri_Masuri.notice.model.entity.QNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class NoticeRepositoryCustomImpl extends QuerydslRepositorySupport implements NoticeRepositoryCustom {

    public NoticeRepositoryCustomImpl() {
        super(CartDetail.class);
    }
    @Override
    public Page<Notice> findList(Pageable pageable) {
        QNotice notice= new QNotice("notice");

        List<Notice> result = from(notice)
                .distinct()
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }
}
