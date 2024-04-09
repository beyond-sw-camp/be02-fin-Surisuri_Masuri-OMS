package com.example.Surisuri_Masuri.email.Service;

import com.example.Surisuri_Masuri.email.Model.EmailConfirmReq;
import com.example.Surisuri_Masuri.email.Model.EmailVerify;
import com.example.Surisuri_Masuri.email.Model.SendEmailReq;
import com.example.Surisuri_Masuri.email.Repository.EmailVerifyRepository;
import com.example.Surisuri_Masuri.exception.EntityException.UserException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    private final EmailVerifyRepository emailVerifyRepository;

    private final UserRepository userRepository;

    private final RedisTemplate<String, String> redisTemplate;

    // 이메일 전송 메소드
    public void sendEmail(SendEmailReq sendEmailReq) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendEmailReq.getEmail());
        message.setSubject("[GIGA COFFEE] 이메일 인증을 완료해주세요 ^3^");

        // UUID도 생성하여 추가적으로 메일 전송
        String uuid = UUID.randomUUID().toString();
        message.setText("http://121.140.125.34:11113/api/user/confirm?email="
                + sendEmailReq.getEmail()
                + "&uuid=" + uuid
                + "&authority=" + sendEmailReq.getAuthority()
        );
        emailSender.send(message);
        create(sendEmailReq.getEmail(),uuid);
    }

    // 이메일 전송 메소드 - 비밀번호 재설정용
    public void sendEmail2(SendEmailReq sendEmailReq) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendEmailReq.getEmail());
        message.setSubject("비밀번호 재설정 요청 이메일입니다.");

        message.setText("http://121.140.125.34:11113/passwordReset/"
                + sendEmailReq.getIdx()
        );
        emailSender.send(message);
    }

    // 이메일 전송 후 인증 여부를 저장하기 위한 메소드
    public void create(String email, String uuid) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set(email, uuid); // email을 키로하고 uuid를 값으로하여 저장
    }

    // 이메일로 전송된 링크를 검증하기 위한 메소드
    public RedirectView verify(EmailConfirmReq emailConfirmReq) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(emailConfirmReq.getEmail());
        if(value.equals(emailConfirmReq.getUuid())){
                update(emailConfirmReq.getEmail(), emailConfirmReq.getAuthority());
                return new RedirectView("http://121.140.125.34:11113");
            }
        else throw new UserException(ErrorCode.UserVerify_0001,String.format("잘못된 인증정보"));
    }

    // 검증된 사용자의 status를 변경하기 위한 메소드
    public void update(String email, String authority) {
        if (authority.equals("ROLE_USER")){
            Optional<User> result = userRepository.findByUserEmail(email);
            if(result.isPresent()) {
                User user = result.get();
                user.changeStatus(true);
                userRepository.save(user);
            }
        }
    }
}
