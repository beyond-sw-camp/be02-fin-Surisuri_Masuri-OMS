package com.example.Surisuri_Masuri.container.repository.QueryDsl;

import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.container.model.entity.Container;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContainerRepositoryCustom {
    public Page<Container> findList(Pageable pageable);
    public Page<Container> findContainerByNameContaining(String name,Pageable pageable);
}
