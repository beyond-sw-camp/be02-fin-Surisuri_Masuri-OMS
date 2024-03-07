package backend.src.main.java.com.example.Surisuri_Masuri.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // User
    UserRegister_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다."),
    UserRegister_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 비밀번호입니다."),
    UserRegister_004(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이름입니다."),
    UserRegister_005(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 storeUuid입니다."),
    UserRegister_006(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 핸드폰 번호입니다."),
    UserRegister_007(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 매장 전화 번호입니다."),
    UserRegister_008(3000, HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    UserRegister_009(3000, HttpStatus.BAD_REQUEST, "글자수 초과"),

    // UserEmail
    UserEmail_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다."),
    UserEmail_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이름입니다."),
    UserEmail_004(3000, HttpStatus.BAD_REQUEST, "존재하지 않는 이메일입니다."),
    UserEmail_005(3000, HttpStatus.BAD_REQUEST, "글자수 초과"),

    // UserPassword
    UserPassword_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다."),
    UserPassword_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이름입니다."),
    UserPassword_004(3000, HttpStatus.BAD_REQUEST, "존재하지 않는 이메일입니다."),
    UserPassword_005(3000, HttpStatus.BAD_REQUEST, "이메일 발송 실패"),
    UserPassword_006(3000, HttpStatus.BAD_REQUEST, "글자수 초과"),

    // UserUpdate
    UserUpdate_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 비밀번호입니다."),
    UserUpdate_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 주소입니다."),
    UserUpdate_004(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 핸드폰 번호입니다"),
    UserUpdate_005(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 전화 번호입니다."),
    UserUpdate_006(3000, HttpStatus.BAD_REQUEST, "비밀번호 수정 실패."),
    UserUpdate_007(3000, HttpStatus.BAD_REQUEST, "매장 주소 변경 실패."),
    UserUpdate_008(3000, HttpStatus.CONFLICT, "핸드폰 번호 수정 실패."),
    UserUpdate_009(3000, HttpStatus.BAD_REQUEST, "매점 번호 수정 실패."),
    UserUpdate_010(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),

    // UserLogin
    UserLogin_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다."),
    UserLogin_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 비밀번호입니다."),
    UserLogin_004(3000, HttpStatus.BAD_REQUEST, "가입되지 않은 이메일입니다."),
    UserLogin_005(3000, HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),
    UserLogin_006(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),

    // UserLogout
    UserLogout_002(3000, HttpStatus.BAD_REQUEST, "토큰 삭제 실패."),

    // Delivery
    DeliveryStatus_002(3000, HttpStatus.BAD_REQUEST, "배송 정보가 존재하지 않습니다."),
    DeliveryStatus_003(3000, HttpStatus.BAD_REQUEST, "배송 정보를 가져오는 것에 실패했습니다."),

    // OrdersList
    OrdersList_002(3000, HttpStatus.NOT_FOUND, "주문 정보가 존재하지 않습니다."),
    OrdersList_003(3000, HttpStatus.BAD_REQUEST, "카트 정보를 가져오는 것에 실패했습니다."),
    OrdersList_004(3000, HttpStatus.NOT_FOUND, "카트 정보가 존재하지 않습니다."),

    // OrdersCreate
    OrdersCreate_002(3000, HttpStatus.NOT_FOUND, "카트 정보가 존재하지 않습니다."),
    OrdersCreate_003(3000, HttpStatus.BAD_REQUEST, "카트 정보를 가져오는 것에 실패했습니다."),
    OrdersCreate_004(3000, HttpStatus.NOT_FOUND, "카트 정보가 존재하지 않습니다."),

    // CartCreate
    CartCreate_002(3000, HttpStatus.NOT_FOUND, "상품 정보가 존재하지 않습니다."),
    CartCreate_003(3000, HttpStatus.BAD_REQUEST, "상품을 카트에 담는 것에 실패했습니다."),

    // CartDelete
    CartDelete_002(3000, HttpStatus.BAD_REQUEST, "카트를 삭제하는 것에 실패했습니다."),
    CartDelete_003(3000, HttpStatus.BAD_REQUEST, "카트 정보가 존재하지 않습니다."),

    // CartUpdate
    CartUpdate_002(3000, HttpStatus.NOT_FOUND, "카트 수정을 실패했습니다."),
    CartUpdate_003(3000, HttpStatus.BAD_REQUEST, "카트 정보가 존재하지 않습니다."),

    // CartList
    CartList_002(3000, HttpStatus.NOT_FOUND, "카트 정보가 존재하지 않습니다."),
    CartList_003(3000, HttpStatus.BAD_REQUEST, "카트 정보 조회에 실패했습니다."),

    // NoticeCreate
    NoticeCreate_002(3000, HttpStatus.NOT_FOUND, "존재하지 않는 형태의 카테고리입니다."),
    NoticeCreate_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 제목입니다."),
    NoticeCreate_004(3000, HttpStatus.NOT_FOUND, "잘못된 형식의 내용입니다."),
    NoticeCreate_005(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),
    NoticeCreate_006(3000, HttpStatus.NOT_FOUND, "공지 사항 생성에 실패했습니다."),

    // NoticeList
    NoticeList_002(3000, HttpStatus.NOT_FOUND, "공지 사항이 존재하지 않습니다."),
    NoticeList_003(3000, HttpStatus.BAD_REQUEST, "공지 사항 정보를 가져오는 것에 실패했습니다."),

    // NoticeUpdate
    NoticeUpdate_002(3000, HttpStatus.BAD_REQUEST, "공지 사항이 존재하지 않습니다."),
    NoticeUpdate_003(3000, HttpStatus.BAD_REQUEST, "공지 사항 수정에 실패했습니다."),
    NoticeUpdate_004(3000, HttpStatus.BAD_REQUEST, "제목 수정에 실패했습니다."),
    NoticeUpdate_005(3000, HttpStatus.BAD_REQUEST, "내용 수정에 실패했습니다."),
    NoticeUpdate_006(3000, HttpStatus.BAD_REQUEST, "카테고리 수정에 실패했습니다."),
    NoticeUpdate_007(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),

    // NoticeDelete
    NoticeDelete_002(3000, HttpStatus.BAD_REQUEST, "공지 사항이 존재하지 않습니다."),
    NoticeDelete_003(3000, HttpStatus.BAD_REQUEST, "공지 사항 삭제에 실패했습니다."),

    // QuestionCreate
    QuestionCreate_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 카테고리입니다."),
    QuestionCreate_003(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 제목입니다."),
    QuestionCreate_004(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 내용입니다."),
    QuestionCreate_005(3000, HttpStatus.BAD_REQUEST, "글자수 초과"),
    QuestionCreate_006(3000, HttpStatus.BAD_REQUEST, "문의 사항 생성에 실패했습니다."),

    // QuestionList
    QuestionList_002(3000, HttpStatus.BAD_REQUEST, "문의 사항이 존재하지 않습니다."),
    QuestionList_003(3000, HttpStatus.BAD_REQUEST, "문의 사항 정보를 가져오는 것에 실패했습니다."),

    // QuestionUpdate
    QuestionUpdate_002(3000, HttpStatus.BAD_REQUEST, "문의 사항이 존재하지 않습니다."),
    QuestionUpdate_003(3000, HttpStatus.BAD_REQUEST, "문의 사항 수정에 실패했습니다."),
    QuestionUpdate_004(3000, HttpStatus.BAD_REQUEST, "제목 수정에 실패했습니다."),
    QuestionUpdate_005(3000, HttpStatus.BAD_REQUEST, "내용 수정에 실패했습니다."),
    QuestionUpdate_006(3000, HttpStatus.BAD_REQUEST, "카테고리 수정에 실패했습니다."),
    QuestionUpdate_007(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),

    // QuestionDelete
    QuestionDelete_002(3000, HttpStatus.BAD_REQUEST, "문의 사항이 존재하지 않습니다."),
    QuestionDelete_003(3000, HttpStatus.BAD_REQUEST, "문의 사항 삭제에 실패했습니다."),

    // QuestionAnswer
    QuestionAnswer_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 내용입니다."),
    QuestionAnswer_003(3000, HttpStatus.BAD_REQUEST, "글자수 초과."),
    QuestionAnswer_004(3000, HttpStatus.BAD_REQUEST, "답변 생성에 실패했습니다."),
    QuestionAnswer_005(3000, HttpStatus.BAD_REQUEST, "답변을 할 문의 사항이 존재하지 않습니다."),

    // ProductCreate
    ProductCreate_002(3000, HttpStatus.BAD_REQUEST, "잘못된 형식의 상품입니다."),
    ProductCreate_003(3000, HttpStatus.BAD_REQUEST, "상품 생성에 실패했습니다."),

    // ProductSearch
    ProductSearch_002(3000, HttpStatus.BAD_REQUEST, "상품이 존재하지 않습니다."),
    ProductSearch_003(3000, HttpStatus.BAD_REQUEST, "상품 조회에 실패했습니다."),
    ;

    private final Integer code;
    private final HttpStatus status;
    private final String message;
}
