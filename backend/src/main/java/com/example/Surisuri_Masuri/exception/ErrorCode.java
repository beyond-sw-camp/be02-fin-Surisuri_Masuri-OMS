package com.example.Surisuri_Masuri.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(1000,HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 처리할 수 없는 경우"),
    CONSTRAINT_VIOLATION(2000,HttpStatus.NOT_FOUND, "잘못된 URL이 지정된 경우"),

    // User
    UserRegister_002(3001, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다."),
    UserRegister_003(3002, HttpStatus.BAD_REQUEST, "잘못된 형식의 비밀번호입니다."),
    UserRegister_004(3003, HttpStatus.BAD_REQUEST, "잘못된 형식의 이름입니다."),
    UserRegister_005(3004, HttpStatus.BAD_REQUEST, "잘못된 형식의 storeUuid입니다."),
    UserRegister_006(3005, HttpStatus.BAD_REQUEST, "잘못된 형식의 핸드폰 번호입니다."),
    UserRegister_007(3006, HttpStatus.BAD_REQUEST, "잘못된 형식의 매장 전화 번호입니다."),
    UserRegister_008(3007, HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    UserRegister_009(3008, HttpStatus.BAD_REQUEST, "글자수 초과"),

    // UserEmail
    UserEmail_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다."),
    UserEmail_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이름입니다."),
    UserEmail_004(3000, HttpStatus.NOT_FOUND, "가입되지 않은 이메일입니다."),
    UserEmail_005(3000, HttpStatus.BAD_REQUEST, "글자수 초과"),

    // UserPassword
    UserPassword_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다."),
    UserPassword_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이름입니다."),
    UserPassword_004(3000, HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
    UserPassword_005(3000, HttpStatus.NOT_FOUND, "이메일 발송 실패"),
    UserPassword_006(3000, HttpStatus.BAD_REQUEST, "글자수 초과"),

    // UserUpdate
    UserUpdate_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 비밀번호입니다."),
    UserUpdate_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 주소입니다."),
    UserUpdate_004(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 핸드폰 번호입니다"),
    UserUpdate_005(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 전화 번호입니다."),
    UserUpdate_006(3000, HttpStatus.BAD_REQUEST, "비밀번호 수정 실패."),
    UserUpdate_007(3000, HttpStatus.BAD_REQUEST, "매장 주소 변경 실패."),
    UserUpdate_008(3000, HttpStatus.BAD_REQUEST, "핸드폰 번호 수정 실패."),
    UserUpdate_009(3000, HttpStatus.BAD_REQUEST, "매점 번호 수정 실패."),
    UserUpdate_010(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),

    // UserLogin
    UserLogin_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다."),
    UserLogin_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 비밀번호입니다."),
    UserLogin_004(3000, HttpStatus.BAD_REQUEST, "가입되지 않은 이메일입니다."),
    UserLogin_005(3000, HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),
    UserLogin_006(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),

    // UserLogout
    UserLogout_002(3000, HttpStatus.NOT_FOUND, "토큰 삭제 실패."),

    // Delivery

    // OrdersList
    OrdersList_002(3000, HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다."),

    // OrdersCreate
    OrdersCreate_002(3000, HttpStatus.BAD_REQUEST, "카트 정보가 존재하지 않습니다."),

    // CartCreate

    // CartDelete

    // CartUpdate

    // CartList

    // NoticeCreate
    NoticeCreate_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 제목입니다."),
    NoticeCreate_004(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 내용입니다."),
    NoticeCreate_005(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),

    // NoticeList

    // NoticeUpdate
    NoticeUpdate_004(3000, HttpStatus.BAD_REQUEST, "제목 수정에 실패했습니다."),
    NoticeUpdate_005(3000, HttpStatus.BAD_REQUEST, "내용 수정에 실패했습니다."),
    NoticeUpdate_007(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),

    // NoticeDelete
    NoticeDelete_002(3000, HttpStatus.BAD_REQUEST, "공지 사항이 존재하지 않습니다."),

    // QuestionCreate
    QuestionCreate_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 카테고리입니다."),
    QuestionCreate_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 제목입니다."),
    QuestionCreate_004(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 내용입니다."),
    QuestionCreate_005(3000, HttpStatus.BAD_REQUEST, "글자수 초과"),

    // QuestionList
    QuestionList_002(3000, HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다."),

    // QuestionUpdate
    QuestionUpdate_003(3000, HttpStatus.BAD_REQUEST, "문의 사항 수정에 실패했습니다."),
    QuestionUpdate_004(3000, HttpStatus.BAD_REQUEST, "제목 수정에 실패했습니다."),
    QuestionUpdate_005(3000, HttpStatus.BAD_REQUEST, "내용 수정에 실패했습니다."),
    QuestionUpdate_006(3000, HttpStatus.BAD_REQUEST, "카테고리 수정에 실패했습니다."),
    QuestionUpdate_007(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),

    // QuestionDelete

    // QuestionAnswer
    QuestionAnswer_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 내용입니다."),
    QuestionAnswer_003(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),
    QuestionAnswer_004(3000, HttpStatus.BAD_REQUEST, "답변 생성에 실패했습니다."),
    QuestionAnswer_005(3000, HttpStatus.BAD_REQUEST, "답변을 할 문의 사항이 존재하지 않습니다."),

    // ProductCreate
    ProductCreate_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품입니다."),

    // ProductSearch


    // ProductList
    ProductList_002(3000, HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다."),

    // ProductUpdate
    ProductUpdate_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품명입니다."),
    ProductUpdate_004(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품 가격입니다."),


    // ProductDelete

    // StoreCreate
    StoreCreate_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 가맹점 이름입니다."),
    StoreCreate_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 가맹점 uuid입니다."),
    StoreCreate_004(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 매니저 이름입니다."),
    StoreCreate_005(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 주소입니다."),
    StoreCreate_006(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 핸드폰 번호입니다."),
    StoreCreate_007(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 가맹점 번호입니다."),

    // StoreSearch
    StoreSearch_002(3000, HttpStatus.BAD_REQUEST, "잘못된 주소입니다."),

    // StoreIncome

    // StoreList

    // StockCreate
    StockCreate_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품 이름입니다."),
    StockCreate_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품 수량입니다."),

    // StockList

    // StockSearch

    // StockUpdate

    // StockDelete

    // ContainerInfo
    ContainerInfo_002(3000, HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다.");

    // ContainerSingleStock

    private final Integer code;
    private final HttpStatus status;
    private final String message;
}

