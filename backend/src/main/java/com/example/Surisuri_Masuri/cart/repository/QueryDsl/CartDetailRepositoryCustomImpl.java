package com.example.Surisuri_Masuri.cart.repository.QueryDsl;

import com.example.Surisuri_Masuri.cart.model.Cart;
import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.cart.model.QCartDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CartDetailRepositoryCustomImpl extends QuerydslRepositorySupport implements CartDetailRepositoryCustom {

    public CartDetailRepositoryCustomImpl() {
        super(CartDetail.class);
    }
    @Override
    public Page<CartDetail> findList(Long cartIdx, Pageable pageable) {
        QCartDetail cartDetail= new QCartDetail("cartDetail");

        List<CartDetail> result = from(cartDetail)
                .where(cartDetail.cart.idx.eq(cartIdx))
                .distinct()
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }
}
