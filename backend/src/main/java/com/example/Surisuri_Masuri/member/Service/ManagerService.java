package com.example.Surisuri_Masuri.member.Service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.ManagerSignUpReq;
import com.example.Surisuri_Masuri.member.Model.ResDtos.ManagerSignUpRes;
import com.example.Surisuri_Masuri.member.Repository.ManagerRepository;
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
public class ManagerService {

    private final ManagerRepository managerRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    Date create = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());
    Date update = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());
    ManagerSignUpRes managerSignUpRes;

    public BaseResponse<ManagerSignUpRes> ManagerSignUp(ManagerSignUpReq managerSignUpReq) {

            // 1. 이메일을 통해 이미 존재하는 회원인지 확인
            if (managerRepository.findByManagerEmail(managerSignUpReq.getManagerEmail()).isPresent()) {
                return BaseResponse.failResponse(7000, "중복된 회원이 있습니다.");
            }

            // 1-1. 생성하여
            Manager manager = Manager.builder()
                    .managerId(managerSignUpReq.getManagerId())
                    .managerPassword(passwordEncoder.encode(managerSignUpReq.getManagerEmail()))
                    .managerEmail(managerSignUpReq.getManagerEmail())
                    .managerName(managerSignUpReq.getManagerName())
                    .managerPhoneNo(managerSignUpReq.getManagerPhoneNo())
                    .managerAuthority("manager")
                    .department("headquater")
                    .updatedAt(create)
                    .createdAt(update)
                    .build();

            // 2. 저장
            managerRepository.save(manager);

            // 3. AccessToken을 생성하여
            String accessToken = JwtUtils.generateAccessToken(manager, secretKey, expiredTimeMs);

            // 4. 응답 Dto 생성을 위한 과정
            Optional<Manager> result = managerRepository.findByManagerEmail(manager.getManagerEmail());

            if (result.isPresent())
            {
                Manager manager2 = result.get();
                managerSignUpRes = ManagerSignUpRes.builder()
                        .token(accessToken)
                        .managerName(manager2.getManagerName())
                        .department(manager2.getDepartment())
                        .managerId(manager2.getManagerId())
                        .build();
            }

        return BaseResponse.successResponse("회원가입 완료", managerSignUpRes);
    }
    public Manager getManagerByManagerEmail (String email){
        Optional<Manager> manager = managerRepository.findByManagerEmail(email);

        if (manager.isPresent()) {
            return manager.get();
        }
        return null;
    }

}

