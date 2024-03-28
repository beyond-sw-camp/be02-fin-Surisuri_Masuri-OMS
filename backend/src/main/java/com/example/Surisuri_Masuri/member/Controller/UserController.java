package com.example.Surisuri_Masuri.member.Controller;

import com.example.Surisuri_Masuri.email.Model.EmailConfirmReq;
import com.example.Surisuri_Masuri.email.Service.EmailService;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.*;
import com.example.Surisuri_Masuri.member.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    // 계정 생성 - Create
    @PostMapping("/user/register")
    public ResponseEntity UserSignUp(@Valid @RequestBody UserSignUpReq userSignUpReq)
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
    public ResponseEntity UserLogin(@RequestBody @Valid LoginReq loginReq)
    {
        return ResponseEntity.ok().body(userService.UserLogin(loginReq));
    }


    // 아이디 찾기
    @GetMapping("/user/findEmail")
    public ResponseEntity findEmail(@Valid @RequestBody UserFindEmailReq userFindEmailReq) {
        return ResponseEntity.ok().body(userService.findEmail(userFindEmailReq));
    }

    // 회원정보 수정
    @PatchMapping("/user/update")
    public ResponseEntity updateUser(@RequestHeader(value = "Authorization") String token, @RequestBody @Valid UserUpdateReq userUpdateReq)
    {
        return ResponseEntity.ok().body(userService.userUpdate(token,userUpdateReq));
    }

    // Password 재설정 이메일 발송
    @PostMapping("/user/findPassword")
    public ResponseEntity findPassword(@RequestBody @Valid FindUserPasswordReq findUserPasswordReq)
    {
        return ResponseEntity.ok().body(userService.findPassword(findUserPasswordReq));
    }

    // Password 재설정 페이지
    @PatchMapping("/user/resetPassword/{idx}")
    public ResponseEntity resetPassword(@PathVariable Long idx, @Valid ResetPasswordReq resetPasswordReq)
    {
        return ResponseEntity.ok().body(userService.resetPassword(idx,resetPasswordReq));
    }

    // 권한 테스트용
    @GetMapping("/user/test")
    public ResponseEntity test()
    {
        return ResponseEntity.ok().body("test1");
    }
}
