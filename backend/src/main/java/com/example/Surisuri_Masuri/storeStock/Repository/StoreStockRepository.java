package com.example.Surisuri_Masuri.storeStock.Repository;

import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import com.example.Surisuri_Masuri.storeStock.Model.ReqDtos.StoreStockUpdateReq;
import com.example.Surisuri_Masuri.storeStock.Repository.QueryDsl.StoreStockRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreStockRepository extends JpaRepository<StoreStock,Long> , StoreStockRepositoryCustom {
    Optional<StoreStock> findStoreStockByProduct_ProductNameAndStore_StoreUuid(String productName,String storeUuid);
    Optional<StoreStock> findStoreStockByProduct_IdxAndStore_StoreUuid(Long productIdx,String storeUuid);
    Optional<StoreStock> deleteStoreStockByProduct_IdxAndStore_StoreUuid(Long productIdx,String storeUuid);

}