package com.example.Surisuri_Masuri.store.Controller;

import com.example.Surisuri_Masuri.store.Model.ReqDtos.StoreCreateReq;
import com.example.Surisuri_Masuri.store.Model.ReqDtos.StoreSearchReq;
import com.example.Surisuri_Masuri.store.Service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class StoreController {

    private final StoreService storeService;

    // 가맹점 등록 - Create
    @PostMapping("/store/create")
    public ResponseEntity StoreCreate(@RequestHeader(value = "Authorization") String token, @RequestBody StoreCreateReq storeCreateReq)
    {
        return ResponseEntity.ok().body(storeService.StoreCreate(token,storeCreateReq));
    }

    // 가맹점 리스트 조회 - List
    @PostMapping("/store/list")
    public ResponseEntity StoreSearch(@RequestHeader(value = "Authorization") String token, Integer page, Integer size)
    {
        return ResponseEntity.ok().body(storeService.StoreList(token,page,size));
    }

    // 가맹점 검색 조회 - Search
    @PostMapping("/store/search")
    public ResponseEntity StoreSearch(@RequestHeader(value = "Authorization") String token, @RequestBody StoreSearchReq storeSearchReq)
    {
        return ResponseEntity.ok().body(storeService.StoreSearch(token,storeSearchReq));
    }




}
