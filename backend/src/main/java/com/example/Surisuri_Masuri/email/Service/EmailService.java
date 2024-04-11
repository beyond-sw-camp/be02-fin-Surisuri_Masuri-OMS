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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    public void sendEmail(SendEmailReq sendEmailReq) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        String authUrl = "http://121.140.125.34:11113/api/user/confirm?email="
                + sendEmailReq.getEmail()
                + "&uuid=" + UUID.randomUUID().toString()
                + "&authority=" + sendEmailReq.getAuthority();
        String htmlContent = "<div style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid #2e8b57; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"
                + "<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"
                + "<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">GIGA COFFEE</span><br />"
                + "<span style=\"color: #00704a;\">메일인증</span> 안내입니다."
                + "</h1>"
                + "<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">"
                + "안녕하세요.<br />"
                + "GIGA COFFEE 에 가입해 주셔서 진심으로 감사드립니다.<br />"
                + "아래 <b style=\"color: #00704a;\">'메일 인증'</b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />"
                + "감사합니다."
                + "</p>"
                + "<a style=\"color: #FFF; text-decoration: none; text-align: center;\" href=\"" + authUrl + "\" target=\"_blank\">"
                + "<p style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #00704a; line-height: 45px; vertical-align: middle; font-size: 16px;\">메일 인증</p>"
                + "</a>"
                + "<div style=\"border-top: 1px solid #DDD; padding: 5px;\">"
                + "<p style=\"font-size: 13px; line-height: 21px; color: #555;\">"
                + "만약 버튼이 정상적으로 클릭되지 않는다면, 아래 링크를 복사하여 접속해 주세요.<br />"
                + authUrl
                + "</p>"
                + "</div>"
                + "</div>";

        message.setContent(htmlContent, "text/html; charset=utf-8");
        message.setSubject("[GIGA COFFEE] 이메일 인증을 완료해주세요 ^3^");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendEmailReq.getEmail()));
        emailSender.send(message);
    }
    public void sendEmail2(SendEmailReq sendEmailReq) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        String authUrl = "http://121.140.125.34:11113/passwordReset/"
                + sendEmailReq.getIdx();
        String htmlContent = "<div style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid #00704a; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"
                + "<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"
                + "<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">GIGA COFFEE</span><br />"
                + "<span style=\"color: #00704a\">메일인증</span> 안내입니다."
                + "</h1>"
                + "<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">"
                + "안녕하세요.<br />"
                + "GIGA COFFEE 계정 비밀번호 재설정을 완료해 주세요.<br />"
                + "아래 <b style=\"color: #00704a\">'비밀번호 재설정'</b> 버튼을 클릭하여 비밀번호 재설정을 완료해 주세요.<br />"
                + "감사합니다."
                + "</p>"
                + "<a style=\"color: #FFF; text-decoration: none; text-align: center;\" href=\"" + authUrl + "\" target=\"_blank\">"
                + "<p style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #00704a; line-height: 45px; vertical-align: middle; font-size: 16px;\">비밀번호 재설정</p>"
                + "</a>"
                + "<div style=\"border-top: 1px solid #DDD; padding: 5px;\">"
                + "<p style=\"font-size: 13px; line-height: 21px; color: #555;\">"
                + "만약 버튼이 정상적으로 클릭되지 않는다면, 아래 링크를 복사하여 접속해 주세요.<br />"
                + authUrl
                + "</p>"
                + "</div>"
                + "</div>";

        message.setContent(htmlContent, "text/html; charset=utf-8");
        message.setSubject("비밀번호 재설정 요청 이메일입니다.");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendEmailReq.getEmail()));
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