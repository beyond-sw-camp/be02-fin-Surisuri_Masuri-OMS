package com.example.Surisuri_Masuri.cart.repository.QueryDsl;

import com.example.Surisuri_Masuri.cart.model.Cart;
import com.example.Surisuri_Masuri.cart.model.CartDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartDetailRepositoryCustom {
    public Page<CartDetail> findList(Long cartIdx, Pageable pageable);
}
