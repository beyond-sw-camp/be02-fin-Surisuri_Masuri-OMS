package com.example.Surisuri_Masuri.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class Test {

    // 계정 생성 - Create
    @GetMapping("/test")
    public ResponseEntity test()
    {
        return ResponseEntity.ok().body("v2");
    }

}
