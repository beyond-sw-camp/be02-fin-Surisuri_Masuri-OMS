package com.example.Surisuri_Masuri.member.Service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.email.Model.SendEmailReq;
import com.example.Surisuri_Masuri.email.Service.EmailService;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.UserSignUpReq;
import com.example.Surisuri_Masuri.member.Model.ResDtos.UserSignUpRes;
import com.example.Surisuri_Masuri.member.Repository.UserRepository;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import com.example.Surisuri_Masuri.store.Repository.StoreRepository;
import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final StoreRepository storeRepository;

    private final EmailService emailService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    Date create = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());
    Date update = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());

    UserSignUpRes userSignUpRes;

    public BaseResponse<UserSignUpRes> UserSignUp(UserSignUpReq userSignUpReq) {

        // 1. 이메일을 통해 이미 존재하는 회원인지 확인
        if (userRepository.findByUserEmail(userSignUpReq.getUserEmail()).isPresent()) {
            return BaseResponse.failResponse(7000, "중복된 회원이 있습니다.");
        }

        // 2. storeUuid를 통해 이미 본사와 계약이 체결되어 uuid가 발급된 회원인지 확인
        else if (storeRepository.findByStoreUuid(userSignUpReq.getStoreUuid()).isPresent()) {

            // 3. storeUuid를 통해 Entity를 찾고, Store 및 User 엔티티의 정보를 수정해서 저장 // 가입되는 과정
            Optional<Store> store = storeRepository.findByStoreUuid(userSignUpReq.getStoreUuid());
            if (store.isPresent()) {

                User user = User.builder()
                        .userName(userSignUpReq.getUserName())
                        .userEmail(userSignUpReq.getUserEmail())
                        .userPassword(passwordEncoder.encode(userSignUpReq.getUserPassword()))
                        .userPhoneNo(userSignUpReq.getUserPhoneNo())
                        .userAuthority("User")
                        .status(false)
                        .createdAt(create)
                        .updatedAt(update)
                        .build();

                userRepository.save(user);

                Store store2 = store.get();

                store2.setStoreAddr(userSignUpReq.getStoreAddr());
                store2.setStorePhoneNo(userSignUpReq.getUserPhoneNo());
                store2.setCreatedAt(create);
                store2.setUpdatedAt(update);
                store2.setUser(user);

                storeRepository.save(store2);

                // 3. AccessToken을 생성하여
                String accessToken = JwtUtils.generateAccessToken(user, secretKey, expiredTimeMs);

                // 4. 이메일에 포함시켜 사용자에게 전달하여 이메일 인증을 요청
                SendEmailReq sendEmailReq = SendEmailReq.builder()
                        .email(user.getUserEmail())
                        .authority(user.getUserAuthority())
                        .accessToken(accessToken)
                        .build();

                // 5. 이메일 전송
                emailService.sendEmail(sendEmailReq);

                // 6. 응답 Dto 생성을 위한 과정
                Optional<User> result = userRepository.findByUserEmail(user.getUserEmail());

                if (result.isPresent()) {
                    user = result.get();
                }

                userSignUpRes = UserSignUpRes.builder()
                        .userName(user.getUsername())
                        .userEmail(user.getUserEmail())
                        .userPhoneNo(user.getUserPhoneNo())
                        .storeUuid(store2.getStoreUuid())
                        .storeAddr(store2.getStoreAddr())
                        .storePhoneNo(store2.getStorePhoneNo())
                        .status(false)
                        .build();

            }
        }
        return BaseResponse.successResponse("이메일 인증 대기중...", userSignUpRes);
    }
        public User getUserByUserEmail (String email){
            Optional<User> user = userRepository.findByUserEmail(email);

            if (user.isPresent()) {
                return user.get();
            }
            return null;
        }

}

