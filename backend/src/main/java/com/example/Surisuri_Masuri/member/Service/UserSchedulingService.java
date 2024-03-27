package com.example.Surisuri_Masuri.member.Service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.exception.EntityException.ContainerStockException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Repository.UserRepository;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import com.example.Surisuri_Masuri.storeStock.Model.ResDtos.StoreStockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSchedulingService {
    private final UserRepository userRepository;
    User user2;

    public BaseResponse resetFirstLogin() {

        // 유통기한이 1주일 남은 식품 상품들을 조회합니다.
        List<User> user = userRepository.findAll();

        for (int i = 0; i < user.size(); i++) {
            user2 = user.get(i);

            if (user2.getFirstLogin().equals(true)) {
                user2.setUserFirstLoginfalse();
                userRepository.save(user2);
                return BaseResponse.successResponse("사용자의 최초 접속 기록을 초기화 하였습니다.", null);
            }
        }

        return BaseResponse.successResponse("초기화할 사용자가 없습니다.", null);
    }

}
