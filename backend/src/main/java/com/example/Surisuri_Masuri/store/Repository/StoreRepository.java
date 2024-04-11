package com.example.Surisuri_Masuri.store.Repository;

import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import com.example.Surisuri_Masuri.store.Repository.QueryDsl.StoreRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long>, StoreRepositoryCustom {
    Optional<Store> findByStoreUuid(String storeUuid);
    Optional<Store> findByStoreName(String storeName);
}