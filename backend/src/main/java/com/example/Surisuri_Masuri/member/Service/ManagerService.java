package com.example.Surisuri_Masuri.member.Service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.exception.EntityException.ContainerException;
import com.example.Surisuri_Masuri.exception.EntityException.ManagerException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.LoginReq;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.ManagerLoginReq;
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

            // 1. 이메일과 아이디를 통해 이미 존재하는 회원인지 확인
            if (managerRepository.findByManagerId(managerSignUpReq.getManagerId()).isPresent()) {
                throw new ManagerException(ErrorCode.ManagerRegister_006,
                        String.format("이미 존재하는 아이디입니다."));
            }

            if (managerRepository.findByManagerEmail(managerSignUpReq.getManagerEmail()).isPresent()) {
            throw new ManagerException(ErrorCode.ManagerRegister_007,
                    String.format("이미 존재하는 이메일입니다."));
            }

            // 1-1. 생성하여
            Manager manager = Manager.builder()
                    .managerId(managerSignUpReq.getManagerId())
                    .managerPassword(passwordEncoder.encode(managerSignUpReq.getManagerPassword()))
                    .managerEmail(managerSignUpReq.getManagerEmail())
                    .managerName(managerSignUpReq.getManagerName())
                    .managerPhoneNo(managerSignUpReq.getManagerPhoneNo())
                    .managerAuthority("ROLE_Manager")
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

        return BaseResponse.successResponse("회원 가입이 완료되었습니다.", managerSignUpRes);
    }

    // 로그인 기능
    public BaseResponse<LoginRes> ManagerLogin(ManagerLoginReq managerLoginReq) {
        LoginRes loginRes = null;

        Optional<Manager> manager = managerRepository.findByManagerId(managerLoginReq.getId());

        if (manager.isEmpty()) {
            throw new ManagerException(ErrorCode.ManagerLogin_003,
                    String.format("잘못된 아이디입니다."));
        }

        if (manager.isPresent() && passwordEncoder.matches(managerLoginReq.getPassword(), manager.get().getPassword()))
        {
            loginRes = LoginRes.builder()
                    .jwtToken(JwtUtils.generateAccessToken(manager.get(), secretKey, expiredTimeMs))
                    .build();
            return BaseResponse.successResponse("정상적으로 로그인 되었습니다.", loginRes);
        }

        else throw new ManagerException(ErrorCode.ManagerLogin_004,
                String.format("잘못된 비밀번호입니다."));
    }

    public Manager getManagerByManagerId (String id){
        Optional<Manager> manager = managerRepository.findByManagerId(id);

        if (manager.isPresent()) {
            return manager.get();
        }
        return null;
    }

}

