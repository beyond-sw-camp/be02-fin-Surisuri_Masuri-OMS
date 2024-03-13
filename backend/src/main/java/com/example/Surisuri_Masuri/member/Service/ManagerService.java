package com.example.Surisuri_Masuri.member.Service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.LoginReq;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.ManagerSignUpReq;
import com.example.Surisuri_Masuri.member.Model.ResDtos.LoginRes;
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

    // 회원가입 기능
    public BaseResponse<ManagerSignUpRes> ManagerSignUp(ManagerSignUpReq managerSignUpReq) {


        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Date create = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());
        Date update = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());

            // 1. 이메일을 통해 이미 존재하는 회원인지 확인
            if (managerRepository.findByManagerId(managerSignUpReq.getManagerEmail()).isPresent()) {
                return BaseResponse.failResponse(7000, "중복된 회원이 있습니다.");
            }

            // 1-1. 생성하여
            Manager manager = Manager.builder()
                    .managerId(managerSignUpReq.getManagerId())
                    .managerPassword(passwordEncoder.encode(managerSignUpReq.getManagerEmail()))
                    .managerEmail(managerSignUpReq.getManagerEmail())
                    .managerName(managerSignUpReq.getManagerName())
                    .managerPhoneNo(managerSignUpReq.getManagerPhoneNo())
                    .managerAuthority("Manager")
                    .department("headquater")
                    .updatedAt(create)
                    .createdAt(update)
                    .build();

            // 2. 저장
            managerRepository.save(manager);

            // 3. AccessToken을 생성하여
            String accessToken = JwtUtils.generateAccessToken(manager, secretKey, expiredTimeMs);

            // 4. 응답 Dto 생성을 위한 과정
            Optional<Manager> result = managerRepository.findByManagerId(manager.getManagerEmail());

            if (result.isPresent())
            {
                manager = result.get();
            }
        ManagerSignUpRes managerSignUpRes = ManagerSignUpRes.builder()
                .token(accessToken)
                .managerName(manager.getManagerName())
                .department(manager.getDepartment())
                .managerId(manager.getManagerId())
                .build();

        return BaseResponse.successResponse("회원가입 완료", managerSignUpRes);
    }

    // 로그인 기능
    public BaseResponse<LoginRes> ManagerLogin(LoginReq managerLoginReq) {
        LoginRes loginRes = null;
        Optional<Manager> manager = managerRepository.findByManagerId(managerLoginReq.getId());
        if (manager.isEmpty()) {
            return BaseResponse.failResponse(7000, "가입되지 않은 회원입니다.");
        } else if (manager.isPresent() && passwordEncoder.matches(managerLoginReq.getPassword(), manager.get().getPassword()))
            ;
        {
            loginRes = LoginRes.builder()
                    .jwtToken(JwtUtils.generateAccessToken(manager.get(), secretKey, expiredTimeMs))
                    .build();
        }
        return BaseResponse.successResponse("정상적으로 로그인 되었습니다.", loginRes);
    }

    public Manager getManagerByManagerId (String id){
        Optional<Manager> manager = managerRepository.findByManagerId(id);

        if (manager.isPresent()) {
            return manager.get();
        }
        return null;
    }

}

