package com.example.Surisuri_Masuri.storeStock.Controller;

import com.example.Surisuri_Masuri.store.Model.ReqDtos.StoreCreateReq;
import com.example.Surisuri_Masuri.store.Model.ResDtos.StoreCreateRes;
import com.example.Surisuri_Masuri.storeStock.Model.ReqDtos.StoreStockCreateReq;
import com.example.Surisuri_Masuri.storeStock.Service.StoreStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class StoreStockController {

    private final StoreStockService storeStockService;

    // 재고 등록
    @PostMapping("/stock/create")
    public ResponseEntity StoreStockCreate(@RequestHeader(value = "Authorization") String token, @RequestBody StoreStockCreateReq storeStockCreateReq)
    {
        return ResponseEntity.ok().body(storeStockService.StoreStockCreate(token,storeStockCreateReq));
    }


    // 전체 재고 조회
    @RequestMapping(method = RequestMethod.GET, value = "/stock/list")
    public ResponseEntity StoreStockList(@RequestHeader(value = "Authorization") String token, Integer page, Integer size) {
        return ResponseEntity.ok().body(storeStockService.StoreStockList(token,page, size));
    }


    // 단일 재고 조회

    // 재고 수정
}
