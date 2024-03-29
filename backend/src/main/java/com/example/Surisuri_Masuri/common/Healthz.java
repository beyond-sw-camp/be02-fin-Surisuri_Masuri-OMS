package com.example.Surisuri_Masuri.common;

import com.example.Surisuri_Masuri.member.Model.ReqDtos.ManagerSignUpReq;
import com.example.Surisuri_Masuri.member.Service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class Healthz {

    // 계정 생성 - Create
    @GetMapping("/healthz")
    public ResponseEntity healthz()
    {
        return ResponseEntity.ok().body(true);
    }

}
