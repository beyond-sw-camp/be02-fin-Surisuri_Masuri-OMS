package com.example.Surisuri_Masuri.member.Controller;

import com.example.Surisuri_Masuri.email.Model.EmailConfirmReq;
import com.example.Surisuri_Masuri.email.Service.EmailService;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.LoginReq;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.UserFindEmailReq;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.UserSignUpReq;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.UserUpdateReq;
import com.example.Surisuri_Masuri.member.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    // 이메일 인증
    @GetMapping(value = "/user/confirm")
    public RedirectView confirm(EmailConfirmReq emailConfirmReq){
        return emailService.verify(emailConfirmReq);
    }

    // 로그인 기능
    @PostMapping("/user/login")
    public ResponseEntity UserLogin(@RequestBody LoginReq loginReq)
    {
        return ResponseEntity.ok().body(userService.UserLogin(loginReq));
    }

    // 아이디 찾기
    @GetMapping("/user/findEmail")
    public ResponseEntity findEmail(@RequestBody UserFindEmailReq userFindEmailReq)
    {
        return ResponseEntity.ok().body(userService.findEmail(userFindEmailReq));
    }

    // 회원정보 수정
    @PatchMapping("/user/update")
    public ResponseEntity updateUser(@RequestHeader(value = "Authorization") String token, @RequestBody UserUpdateReq userUpdateReq)
    {
        return ResponseEntity.ok().body(userService.userUpdate(token,userUpdateReq));
    }

    // 권한 테스트용
    @GetMapping("/user/test")
    public ResponseEntity test()
    {
        return ResponseEntity.ok().body("test1");
    }
}
