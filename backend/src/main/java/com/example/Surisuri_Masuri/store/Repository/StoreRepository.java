package com.example.Surisuri_Masuri.store.Repository;

import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
    Optional<Store> findByStoreUuid(String storeUuid);
}