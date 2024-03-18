package com.example.Surisuri_Masuri.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // Common
    // 401
    INVALID_TOKEN(1000,HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),

    // 401
    INVALID_PERMISIION(1001,HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

    // 404
    CONSTRAINT_VIOLATION(1002,HttpStatus.NOT_FOUND, "주소를 잘못 입력했습니다."),

    // 409
    // DUPLICATED_USER(1003,HttpStatus.CONFLICT,"이미 존재하는 사용자입니다."),

    // 500
    INTERNAL_SERVER_ERROR(1100,HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 처리할 수 없는 경우"),

    // 글자수 초과
    CommonError_01(3012, HttpStatus.BAD_REQUEST, "글자수 초과"),

    //////////////////////////////////////////////////////////////////////////////////

    // User Email
    UserRegister_002(3001, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다."),

    // User PassWord
    UserRegister_003(3003, HttpStatus.BAD_REQUEST, "잘못된 형식의 비밀번호입니다."),

    // User Name
    UserRegister_005(3004, HttpStatus.BAD_REQUEST, "잘못된 형식의 이름입니다."),

    // User PhoneNo
    UserRegister_006(3005, HttpStatus.BAD_REQUEST, "잘못된 형식의 휴대폰 전화번호입니다."),

    // Store UUID
    UserRegister_007(3006, HttpStatus.BAD_REQUEST, "잘못된 형식의 storeUuid입니다."),
    UserRegister_008(3007, HttpStatus.BAD_REQUEST, "존재하지 않는 storeUuid입니다."),
    UserRegister_009(3008, HttpStatus.CONFLICT, "가게 정보가 이미 등록되어있습니다."),

    // Store Addr
    UserRegister_010(3009, HttpStatus.BAD_REQUEST, "잘못된 형식의 주소 정보입니다."),

    // Store PhoneNo & Store ManagerPhoneNo
    UserRegister_011(3010, HttpStatus.BAD_REQUEST, "잘못된 형식의 핸드폰 번호입니다."),
    UserRegister_012(3011, HttpStatus.BAD_REQUEST, "잘못된 형식의 매장 전화 번호입니다."),


    // UserEmail 찾기
    UserEmail_002(3012, HttpStatus.BAD_REQUEST, "잘못된 형식의 이름입니다."),
    UserEmail_003(3013, HttpStatus.BAD_REQUEST, "잘못된 형식의 전화번호입니다."),
    UserEmail_004(3014, HttpStatus.NOT_FOUND, "가입되지 않은 회원입니다."),

    // UserPassword 찾기
    UserPassword_002(3015, HttpStatus.BAD_REQUEST, "잘못된 형식의 이름 입니다."),
    UserPassword_003(3016, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일 입니다."),
    UserPassword_004(3017, HttpStatus.NOT_FOUND, "가입되지 않은 회원 입니다."),

    // UserUpdate
    UserUpdate_002(3018, HttpStatus.BAD_REQUEST, "잘못된 형식의 비밀번호입니다."),
    UserUpdate_003(3019, HttpStatus.BAD_REQUEST, "잘못된 형식의 주소입니다."),
    UserUpdate_004(3020, HttpStatus.BAD_REQUEST, "잘못된 형식의 핸드폰 번호입니다"),
    UserUpdate_005(3021, HttpStatus.BAD_REQUEST, "잘못된 형식의 전화 번호입니다."),

    // UserLogin
    UserLogin_002(3027, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다."),
    UserLogin_003(3029, HttpStatus.BAD_REQUEST, "가입되지 않은 이메일입니다."),
    UserLogin_004(3030, HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),

    // Manager
    ManagerRegister_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 사원 이름입니다."),
    ManagerRegister_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 부서 이름입니다."),
    ManagerRegister_004(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 아이디입니다."),
    ManagerRegister_005(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 비밀번호입니다."),

    // ManagerLogin
    ManagerLogin_002(3027, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다."),
    ManagerLogin_003(3029, HttpStatus.BAD_REQUEST, "가입되지 않은 이메일입니다."),
    ManagerLogin_004(3030, HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),

    // Delivery
    Delivery_002(3030, HttpStatus.BAD_REQUEST, "잘못된 형식의 배송 상태 값입니다."),
    Delivery_003(3030, HttpStatus.BAD_REQUEST, "잘못된 형식의 날짜 값입니다."),

    // OrdersList
    // OrdersList_002(3033, HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다."),

    // OrdersCreate
    OrdersCreate_002(3034, HttpStatus.BAD_REQUEST, "카트 정보가 존재하지 않습니다."),
    OrdersCreate_003(3034, HttpStatus.BAD_REQUEST, "카트가 비어있습니다."),

    // OrdersPayment
    // OrdersPayment_002(3034, HttpStatus.BAD_REQUEST, "카트가 비어있습니다."),

    // CartCreate
    CartCreate_002(3034, HttpStatus.BAD_REQUEST, "상품 정보가 존재하지 않습니다."),
    CartCreate_003(3034, HttpStatus.BAD_REQUEST, "상품을 카트에 담는 것에 실패했습니다."),

    // CartDelete
    CartDelete_002(3034, HttpStatus.BAD_REQUEST, "카트를 삭제하는 것에 실패했습니다."),
    CartDelete_003(3034, HttpStatus.BAD_REQUEST, "카트 정보가 존재하지 않습니다."),

    // CartUpdate
    CartUpdate_002(3034, HttpStatus.BAD_REQUEST, "카트 수정을 실패했습니다."),
    CartUpdate_003(3034, HttpStatus.BAD_REQUEST, "카트 정보가 존재하지 않습니다."),

    // CartList
    CartList_002(3034, HttpStatus.BAD_REQUEST, "카트 정보가 존재하지 않습니다."),
    CartList_003(3034, HttpStatus.BAD_REQUEST, "카트 정보 조회에 실패했습니다."),

    // NoticeCreate
    NoticeCreate_002(3035, HttpStatus.BAD_REQUEST, "잘못된 형식의 카테고리입니다."),
    NoticeCreate_003(3035, HttpStatus.BAD_REQUEST, "잘못된 형식의 제목입니다."),
    NoticeCreate_004(3036, HttpStatus.BAD_REQUEST, "잘못된 형식의 내용입니다."),

    // NoticeList
    NoticeList_002(3045, HttpStatus.BAD_REQUEST, "페이지 크기는 1 이상이어야 합니다."),
    NoticeList_003(3046, HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다."),
    NoticeList_004(3047, HttpStatus.BAD_REQUEST, "요청한 페이지 범위를 초과했습니다."),

    // NoticeUpdate
    NoticeUpdate_002(3036, HttpStatus.BAD_REQUEST, "공지 사항이 존재하지 않습니다."),
    NoticeUpdate_003(3038, HttpStatus.BAD_REQUEST, "잘못된 형식의 제목입니다."),
    NoticeUpdate_004(3039, HttpStatus.BAD_REQUEST, "잘못된 형식의 내용입니다."),
    NoticeUpdate_005(3040, HttpStatus.BAD_REQUEST, "글자수 초과."),

    // NoticeDelete
    NoticeDelete_002(3041, HttpStatus.BAD_REQUEST, "공지 사항이 존재하지 않습니다."),

    // QuestionCreate
    QuestionCreate_002(3042, HttpStatus.BAD_REQUEST, "잘못된 형식의 카테고리입니다."),
    QuestionCreate_003(3043, HttpStatus.BAD_REQUEST, "잘못된 형식의 제목입니다."),
    QuestionCreate_004(3044, HttpStatus.BAD_REQUEST, "잘못된 형식의 내용입니다."),
    QuestionCreate_005(3045, HttpStatus.BAD_REQUEST, "글자수 초과"),

    // QuestionList
    QuestionList_002(3045, HttpStatus.BAD_REQUEST, "페이지 크기는 1 이상이어야 합니다."),
    QuestionList_003(3046, HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다."),
    QuestionList_004(3047, HttpStatus.BAD_REQUEST, "요청한 페이지 범위를 초과했습니다."),

    // QuestionUpdate
    QuestionUpdate_002(3048, HttpStatus.BAD_REQUEST, "잘못된 형식의 제목입니다."),
    QuestionUpdate_003(3049, HttpStatus.BAD_REQUEST, "잘못된 형식의 내용입니다."),
    QuestionUpdate_004(3050, HttpStatus.BAD_REQUEST, "잘못된 형식의 카테고리입니다."),

    // QuestionDelete
    QuestionDelete_002(3045, HttpStatus.BAD_REQUEST, "공지사항이 존재하지 않습니다."),

    // QuestionAnswer
    QuestionAnswer_002(3052, HttpStatus.BAD_REQUEST, "잘못된 형식의 내용입니다."),
    QuestionAnswer_003(3055, HttpStatus.BAD_REQUEST, "답변을 할 문의 사항이 존재하지 않습니다."),

    // ProductCreate
    ProductCreate_002(3056, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품입니다."),

    // ProductSearch
    ProductSearch_002(3056, HttpStatus.BAD_REQUEST, "상품이 존재하지 않습니다."),

    // ProductList
    ProductList_002(3045, HttpStatus.BAD_REQUEST, "페이지 크기는 1 이상이어야 합니다."),
    ProductList_003(3046, HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다."),
    ProductList_004(3047, HttpStatus.BAD_REQUEST, "요청한 페이지 범위를 초과했습니다."),

    // ProductUpdate
    ProductUpdate_002(3058, HttpStatus.BAD_REQUEST, "상품이 존재하지 않습니다."),
    ProductUpdate_003(3058, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품명입니다."),
    ProductUpdate_004(3059, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품 가격입니다."),

    // ProductDelete
    ProductDelete_002(3058, HttpStatus.BAD_REQUEST, "상품이 존재하지 않습니다."),

    // StoreCreate
    StoreCreate_002(3060, HttpStatus.BAD_REQUEST, "잘못된 형식의 가맹점 이름입니다."),
    StoreCreate_003(3061, HttpStatus.BAD_REQUEST, "잘못된 형식의 가맹점 uuid입니다."),

    // StoreSearch
    StoreSearch_002(3066, HttpStatus.BAD_REQUEST, "잘못된 주소입니다."),
    StoreSearch_003(3066, HttpStatus.BAD_REQUEST, "검색 결과가 존재하지 않습니다"),

    // StoreList
    StoreList_002(3045, HttpStatus.BAD_REQUEST, "페이지 크기는 1 이상이어야 합니다."),
    StoreList_003(3046, HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다."),
    StoreList_004(3047, HttpStatus.BAD_REQUEST, "요청한 페이지 범위를 초과했습니다."),

    // StockCreate
    StockCreate_002(3067, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품 이름입니다."),
    StockCreate_003(3068, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품 수량입니다."),
    StockCreate_004(3068, HttpStatus.BAD_REQUEST, "존재하지 않는 상품입니다."),

    // StockList
    StockList_002(3045, HttpStatus.BAD_REQUEST, "페이지 크기는 1 이상이어야 합니다."),
    StockList_003(3046, HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다."),
    StockList_004(3047, HttpStatus.BAD_REQUEST, "요청한 페이지 범위를 초과했습니다."),

    // StockRead
    StockRead_002(3047, HttpStatus.BAD_REQUEST, "재고 정보가 존재하지 않습니다."),

    // StockUpdate
    StockUpdate_002(3047, HttpStatus.BAD_REQUEST, "재고 정보가 존재하지 않습니다."),
    StockUpdate_003(3047, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품 이름입니다."),
    StockUpdate_004(3047, HttpStatus.BAD_REQUEST, "잘못된 형식의 재고 수량입니다."),

    // StockDelete
    StockDelete_002(3047, HttpStatus.BAD_REQUEST, "재고 정보가 존재하지 않습니다."),

    // ContainerList
    ContainerList_002(3045, HttpStatus.BAD_REQUEST, "페이지 크기는 1 이상이어야 합니다."),
    ContainerList_003(3046, HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다."),
    ContainerList_004(3047, HttpStatus.BAD_REQUEST, "요청한 페이지 범위를 초과했습니다."),

    // ContainerSingleStock
    ContainerSingleStock_002(3047, HttpStatus.BAD_REQUEST, "재고 정보가 존재하지 않습니다."),

    // ContainerCreate
    ContainerCreate_002(3069, HttpStatus.BAD_REQUEST, "잘못된 형식의 창고 이름입니다."),
    ContainerCreate_003(3069, HttpStatus.BAD_REQUEST, "잘못된 형식의 창고 복잡도 입니다.");

    private final Integer code;
    private final HttpStatus status;
    private final String message;
}

