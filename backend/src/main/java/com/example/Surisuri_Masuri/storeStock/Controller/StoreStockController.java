package com.example.Surisuri_Masuri.storeStock.Controller;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.storeStock.Model.ReqDtos.*;
import com.example.Surisuri_Masuri.storeStock.Service.StoreStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class StoreStockController {

    private final StoreStockService storeStockService;

    // 재고 등록
    @PostMapping("/stock/create")
    public ResponseEntity storeStockCreate(@RequestHeader(value = "Authorization") String token, @RequestBody StoreStockCreateReq storeStockCreateReq) {
        return ResponseEntity.ok().body(storeStockService.storeStockCreate(token, storeStockCreateReq));
    }


    // 전체 재고 조회
    @RequestMapping(method = RequestMethod.GET, value = "/stock/list")
    public ResponseEntity storeStockList(@RequestHeader(value = "Authorization") String token, Integer page, Integer size) {
        return ResponseEntity.ok().body(storeStockService.storeStockList(token, page, size));
    }

    // 단일 재고 조회
    @RequestMapping(method = RequestMethod.GET, value = "/stock/search")
    public ResponseEntity storeStockSearch(@RequestHeader(value = "Authorization") String token, @RequestBody StoreStockSearchReq storeStockSearchReq) {
        return ResponseEntity.ok().body(storeStockService.storeStockSearch(token, storeStockSearchReq));
    }

    // 재고 수정
    @PatchMapping("/stock/update")
    public ResponseEntity storeStockUpdate(@RequestHeader(value = "Authorization") String token, @RequestBody StoreStockUpdateReq storeStockUpdateReq) {
        return ResponseEntity.ok().body(storeStockService.storeStockUpdate(token, storeStockUpdateReq));
    }


    // 재고 삭제
    @DeleteMapping ("/stock/delete")
    public ResponseEntity storeStockDelete(@RequestHeader(value = "Authorization") String token, @RequestBody StoreStockDeleteReq storeStockDeleteReq) {
        return ResponseEntity.ok().body(storeStockService.storeStockDelete(token, storeStockDeleteReq));
    }

    @GetMapping("/nearExpiredFoodStocks")
    public ResponseEntity<BaseResponse<List<ExpiredFoodStockDto>>> findNearExpiredFoodStocks(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(storeStockService.findExpiringFoodStocks(token));
    }

}

