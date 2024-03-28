package com.example.Surisuri_Masuri.member.Service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.email.Model.SendEmailReq;
import com.example.Surisuri_Masuri.email.Service.EmailService;
import com.example.Surisuri_Masuri.exception.EntityException.ManagerException;
import com.example.Surisuri_Masuri.exception.EntityException.UserException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Model.ReqDtos.*;
import com.example.Surisuri_Masuri.member.Model.ResDtos.*;
import com.example.Surisuri_Masuri.member.Repository.UserRepository;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import com.example.Surisuri_Masuri.store.Repository.StoreRepository;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final StoreRepository storeRepository;

    private final EmailService emailService;

    Optional<User> compare1;
    Optional<User> compare2;

    UserFindEmailRes userFindEmailRes;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    UserSignUpRes userSignUpRes;

    List<DiscardedProduct> discardedProduct2 = new ArrayList<>();

    // 회원가입 기능
    public BaseResponse<UserSignUpRes> UserSignUp(UserSignUpReq userSignUpReq) {

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Date update = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());
        Date create = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());

        // 1. 이메일을 통해 이미 존재하는 회원인지 확인
        if (userRepository.findByUserEmail(userSignUpReq.getUserEmail()).isPresent()) {
            throw new UserException(ErrorCode.UserRegister_0010,
                    String.format("이미 존재하는 이메일 입니다."));
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
                        .userAuthority("ROLE_User")
                        .store(store.get())
                        .status(false)
                        .createdAt(create)
                        .updatedAt(update)
                        .build();

                userRepository.save(user);

                Store store2 = store.get();

                store2.setStoreAddr(userSignUpReq.getStoreAddr());
                store2.setStorePhoneNo(userSignUpReq.getUserPhoneNo());
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
        return BaseResponse.successResponse("이메일 인증이 완료되면 회원가입이 완료됩니다.", userSignUpRes);
    }

    // 로그인 기능
    public BaseResponse<LoginRes> UserLogin(LoginReq userLoginReq) {
        LoginRes loginRes = null;

        discardedProduct2.clear();

        Optional<User> user = userRepository.findByUserEmail(userLoginReq.getId());

        if (user.isEmpty()) {
            throw new UserException(ErrorCode.UserLogin_003,
                    String.format("가입되지 않은 이메일입니다."));
        }

        if (user.isPresent() &&
                !passwordEncoder.matches(userLoginReq.getPassword(), user.get().getPassword()))
        {
            throw new UserException(ErrorCode.UserLogin_005,
                    String.format("비밀번호가 일치하지 않습니다."));
        }

        if (user.isPresent() &&
                passwordEncoder.matches(userLoginReq.getPassword(), user.get().getPassword())
                && user.get().getStatus().equals(true))
        {
            Optional<Store> store = storeRepository.findByStoreName(user.get().getStore().getStoreName());

            List<StoreStock> storeStockList = store.get().getStoreStocks();

            for(int i = 0 ; i< storeStockList.size(); i++) {
                StoreStock storeStock = storeStockList.get(i);

                if (storeStock.getIsDiscarded().equals(true)) {
                    DiscardedProduct discardedProduct = DiscardedProduct
                            .builder()
                            .productName(storeStock.getProduct().getProductName())
                            .expiredDate(storeStock.getExpiredAt())
                            .build();

                    discardedProduct2.add(discardedProduct);
                }
                if (discardedProduct2.size() > 0) {
                    loginRes = LoginRes.builder()
                            .jwtToken(JwtUtils.generateAccessToken(user.get(), secretKey, expiredTimeMs))
                            .discardedProduct(discardedProduct2)
                            .build();
                }
                else {
                    loginRes = LoginRes.builder()
                            .jwtToken(JwtUtils.generateAccessToken(user.get(), secretKey, expiredTimeMs))
                            .build();
                }
            }
            return BaseResponse.successResponse("정상적으로 로그인 되었습니다.", loginRes);
        }
        else throw new ManagerException(ErrorCode.UserLogin_004,
                String.format("이메일 인증이 필요합니다."));
    }

    // 이메일 찾기 기능
    public BaseResponse<UserFindEmailRes> findEmail(UserFindEmailReq userFindEmailReq) {
        compare1 = userRepository.findByUserName(userFindEmailReq.getUserName());
        compare2 = userRepository.findByUserPhoneNo(userFindEmailReq.getUserPhoneNo());

        if(compare1.isEmpty() || compare2.isEmpty()) {
            throw new ManagerException(ErrorCode.UserEmail_004,
                    String.format("가입되지 않은 회원입니다."));
        }
        if (compare1.equals(compare2)) {
             userFindEmailRes = UserFindEmailRes
                    .builder()
                    .userEmail(compare1.get().getUserEmail())
                    .build();
        }
            return BaseResponse.successResponse("요청하신 회원 정보입니다.", userFindEmailRes);

    }

    // 회원정보 수정 기능
    public BaseResponse<UserUpdateRes> userUpdate(String token, UserUpdateReq userUpdateReq) {

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Date update = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());
        Date create = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());

        token = JwtUtils.replaceToken(token);
        String email = JwtUtils.getUserEmail(token, secretKey);
        Optional<User> user = userRepository.findByUserEmail(email);

        if (user.isPresent()) {

            User user2 = user.get();
            User user3 = user.get();

            Long idx = user3.getIdx(); // store에서 사용하는 키값

            if (!userUpdateReq.getUserPassword().equals("")) {
                user2.setUserPassword(passwordEncoder.encode(userUpdateReq.getUserPassword()));
                userRepository.save(user2);
            } else if (!userUpdateReq.getUserPhoneNo().equals("")) {
                user2.setUserPhoneNo(userUpdateReq.getUserPhoneNo());
                userRepository.save(user2);
            }

            Optional<Store> store = storeRepository.findById(idx);
            Store store2 = store.get();

            if (!userUpdateReq.getStoreAddr().equals("")) {
                store2.setStoreAddr(userUpdateReq.getStoreAddr());
            }
            if (!userUpdateReq.getStorePhoneNo().equals("")) {
                store2.setStorePhoneNo(userUpdateReq.getUserPhoneNo());
            }
            store2.setUpdatedAt(update);
            storeRepository.save(store2);


            UserUpdateRes userUpdateRes = UserUpdateRes
                    .builder()
                    .userPassword(userUpdateReq.getUserPassword())
                    .storeAddr(userUpdateReq.getStoreAddr())
                    .userPhoneNo(userUpdateReq.getUserPhoneNo())
                    .storePhoneNo(userUpdateReq.getStorePhoneNo())
                    .build();
            BaseResponse baseResponse = BaseResponse.successResponse("회원 정보가 수정 되었습니다.", userUpdateRes);


            return baseResponse;
        } else {
            return BaseResponse.failResponse(7000, "요청 실패");
        }
    }

    // 회원 비밀번호 찾기 기능
    public BaseResponse<FindUserPasswordRes> findPassword(FindUserPasswordReq findUserPasswordReq) {

        Optional<User> userResult = userRepository.findByUserEmail(findUserPasswordReq.getUserEmail());

        if (userResult.isPresent()) {
            User user = userResult.get();

            if (user.getUsername() == findUserPasswordReq.getUserName()) {

                Long idx = user.getIdx();
                String userEmail = user.getUserEmail();
                SendEmailReq sendEmailReq = SendEmailReq.builder()
                        .idx(idx)
                        .email(userEmail)
                        .build();

                // 5. 이메일 전송
                emailService.sendEmail2(sendEmailReq);

                FindUserPasswordRes findUserPasswordRes = FindUserPasswordRes.builder()
                        .idx(idx)
                        .build();

                BaseResponse baseResponse = BaseResponse.successResponse("가입하신 이메일로 비밀번호 재설정 링크를 보내드렸습니다.", findUserPasswordRes);

                return baseResponse;
            } else {
                return BaseResponse.failResponse(444, "잘못된 이름 형식입니다.");
            }

        }

        else
            throw new ManagerException(ErrorCode.UserPassword_004,
                    String.format("가입되지 않은 회원입니다."));

    }

    // 비밀번호 재설정 기능
    public BaseResponse<ResetPasswordRes> resetPassword(Long idx, ResetPasswordReq resetPasswordReq) {
        Optional<User> user = userRepository.findById(idx);
        if (user.isPresent()) {

            User user2= user.get();
            user2.setUserPassword(passwordEncoder.encode(resetPasswordReq.getUserPassword()));
            userRepository.save(user2);

            ResetPasswordRes resetPasswordRes = ResetPasswordRes
                    .builder()
                    .password(resetPasswordReq.getUserPassword())
                    .build();

            BaseResponse baseResponse = BaseResponse.successResponse("비밀번호가 재설정 되었습니다.", resetPasswordRes);

            return baseResponse;
        }

        else
            throw new ManagerException(ErrorCode.UserPassword_004,
                    String.format("가입되지 않은 회원입니다."));

    }

    public User getUserByUserEmail (String email){
        Optional<User> user = userRepository.findByUserEmail(email);

        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByUserEmail(username);
        User user = null;
        if(result.isPresent()) {
            user = result.get();
        }

        return user;
    }
}

