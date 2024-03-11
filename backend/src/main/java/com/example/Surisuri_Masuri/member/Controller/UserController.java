package com.example.Surisuri_Masuri.member.Controller;

import com.example.Surisuri_Masuri.email.Model.EmailConfirmReq;
import com.example.Surisuri_Masuri.email.Service.EmailService;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.LoginReq;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.UserSignUpReq;
import com.example.Surisuri_Masuri.member.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    // 계정 생성 - Create
    @PostMapping("/user/register")
    public ResponseEntity UserSignUp(@RequestBody UserSignUpReq userSignUpReq)
    {
        return ResponseEntity.ok().body(userService.UserSignUp(userSignUpReq));
    }


    // 로그인 기능
    @PostMapping("/user/login")
    public ResponseEntity CustomerLogin(@RequestBody LoginReq loginReq)
    {
        return ResponseEntity.ok().body(userService.CustomerLogin(loginReq));
    }

    // 이메일 인증
    @GetMapping(value = "/user/confirm")
    public RedirectView confirm(EmailConfirmReq emailConfirmReq){
        return emailService.verify(emailConfirmReq);
    }

}
