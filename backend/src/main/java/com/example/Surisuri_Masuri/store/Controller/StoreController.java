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
    public ResponseEntity StoreCreate(@RequestHeader(value = "AccessToken") String token, @RequestBody StoreCreateReq storeCreateReq)
    {
        return ResponseEntity.ok().body(storeService.StoreCreate(token,storeCreateReq));
    }

    // 가맹점 리스트 조회 - List
    @GetMapping("/store/list")
    public ResponseEntity StoreSearch(@RequestHeader(value = "AccessToken") String token, Integer page, Integer size)
    {
        return ResponseEntity.ok().body(storeService.StoreList(token,page,size));
    }

    // 가맹점 검색 조회 - Search
    @GetMapping("/store/search")
    public ResponseEntity StoreSearch(@RequestHeader(value = "AccessToken") String token, StoreSearchReq storeSearchReq, Integer page, Integer size)
    {
        return ResponseEntity.ok().body(storeService.StoreSearch(token,storeSearchReq,page,size));
    }

}
