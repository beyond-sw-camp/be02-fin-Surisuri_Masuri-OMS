package com.example.Surisuri_Masuri.storeStock.Controller;

import com.example.Surisuri_Masuri.store.Model.ReqDtos.StoreCreateReq;
import com.example.Surisuri_Masuri.store.Model.ResDtos.StoreCreateRes;
import com.example.Surisuri_Masuri.storeStock.Model.ReqDtos.StoreStockCreateReq;
import com.example.Surisuri_Masuri.storeStock.Model.ReqDtos.StoreStockDeleteReq;
import com.example.Surisuri_Masuri.storeStock.Model.ReqDtos.StoreStockSearchReq;
import com.example.Surisuri_Masuri.storeStock.Model.ReqDtos.StoreStockUpdateReq;
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
    public ResponseEntity StoreStockCreate(@RequestHeader(value = "Authorization") String token, @RequestBody StoreStockCreateReq storeStockCreateReq) {
        return ResponseEntity.ok().body(storeStockService.StoreStockCreate(token, storeStockCreateReq));
    }


    // 전체 재고 조회
    @RequestMapping(method = RequestMethod.GET, value = "/stock/list")
    public ResponseEntity StoreStockList(@RequestHeader(value = "Authorization") String token, Integer page, Integer size) {
        return ResponseEntity.ok().body(storeStockService.StoreStockList(token, page, size));
    }

    // 단일 재고 조회
    @RequestMapping(method = RequestMethod.GET, value = "/stock/search")
    public ResponseEntity StoreStockSearch(@RequestHeader(value = "Authorization") String token, @RequestBody StoreStockSearchReq storeStockSearchReq) {
        return ResponseEntity.ok().body(storeStockService.StoreStockSearch(token, storeStockSearchReq));
    }

    // 재고 수정
    @PatchMapping("/stock/update")
    public ResponseEntity StoreStockUpdate(@RequestHeader(value = "Authorization") String token, @RequestBody StoreStockUpdateReq storeStockUpdateReq) {
        return ResponseEntity.ok().body(storeStockService.StoreStockUpdate(token, storeStockUpdateReq));
    }


    // 재고 삭제
    @DeleteMapping ("/stock/delete")
    public ResponseEntity StoreStockDelete(@RequestHeader(value = "Authorization") String token, @RequestBody StoreStockDeleteReq storeStockDeleteReq) {
        return ResponseEntity.ok().body(storeStockService.StoreStockDelete(token, storeStockDeleteReq));
    }


}

