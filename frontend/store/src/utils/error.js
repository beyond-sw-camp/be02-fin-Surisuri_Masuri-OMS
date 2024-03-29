// error.js
export function getErrorMessage(errorCode) {
  const errorMessages = {
    900: "유효하지 않은 입력값입니다.",
    1001: "권한이 없습니다.",
    1002: "액세스가 차단되었습니다.",
    1003: "주소를 잘못 입력했습니다.",
    1004: "서버에서 처리할 수 없는 경우",
    1005: "글자수 초과",
    3006: "존재하지 않는 storeUuid입니다.",
    3007: "가게 정보가 이미 등록되어 있습니다.",
    3013: "가입되지 않은 회원입니다.",
    3016: "가입되지 않은 회원입니다.",
    3022: "가입되지 않은 이메일입니다.",
    3023: "이메일 인증이 필요합니다.",
    3024: "비밀번호가 틀렸습니다.",
    3028: "이미 존재하는 아이디입니다.",
    3029: "이미 존재하는 이메일입니다.",
    3033: "카트 정보가 존재하지 않습니다.",
    3034: "카트가 비어있습니다.",
    3035: "카트에 상품 정보가 존재하지 않습니다.",
    3036: "카트에 상품을 담는 것을 실패했습니다.",
    3104: "카트에 상품을 최소 1개 이상 추가해야 합니다.",
    3037: "카트 삭제를 실패했습니다.",
    3038: "카트 정보가 존재하지 않습니다.",
    3039: "카트 수정을 실패했습니다.",
    3040: "카트 정보가 존재하지 않습니다.",
    3041: "카트 정보가 존재하지 않습니다.",
    3042: "카트 정보 조회에 실패했습니다.",
    3046: "페이지 크기는 1 이상이어야 합니다.",
    3047: "잘못된 페이지 번호입니다.",
    3048: "요청한 페이지 범위를 초과했습니다.",
    3049: "공지 사항이 존재하지 않습니다.",
    3052: "글자수 초과.",
    3053: "공지 사항이 존재하지 않습니다.",
    3057: "글자수 초과",
    3058: "페이지 크기는 1 이상이어야 합니다.",
    3059: "잘못된 페이지 번호입니다.",
    3060: "요청한 페이지 범위를 초과했습니다.",
    3063: "존재하지 않는 문의사항입니다.",
    3064: "공지사항이 존재하지 않습니다.",
    3066: "답변을 할 문의 사항이 존재하지 않습니다.",
    3068: "상품이 존재하지 않습니다.",
    3069: "페이지 크기는 1 이상이어야 합니다.",
    3070: "잘못된 페이지 번호입니다.",
    3071: "요청한 페이지 범위를 초과했습니다.",
    3072: "상품이 존재하지 않습니다.",
    3075: "상품이 존재하지 않습니다.",
    3076: "가입되지 않은 관리자입니다.",
    3078: "잘못된 주소입니다.",
    3079: "검색 결과가 존재하지 않습니다",
    3080: "페이지 크기는 1 이상이어야 합니다.",
    3081: "잘못된 페이지 번호입니다.",
    3082: "요청한 페이지 범위를 초과했습니다.",
    3083: "가입되지 않은 관리자입니다.",
    3085: "존재하지 않는 상품입니다.",
    3086: "페이지 크기는 1 이상이어야 합니다.",
    3087: "잘못된 페이지 번호입니다.",
    3088: "요청한 페이지 범위를 초과했습니다.",
    3089: "재고 정보가 존재하지 않습니다.",
    3090: "재고 정보가 존재하지 않습니다.",
    3093: "재고 정보가 존재하지 않습니다.",
    3094: "페이지 크기는 1 이상이어야 합니다.",
    3095: "잘못된 페이지 번호입니다.",
    3096: "요청한 페이지 범위를 초과했습니다.",
    3097: "재고 정보가 존재하지 않습니다.",
    3100: "이미 존재하는 창고 주소입니다.",
    3101: "이미 존재하는 창고 이름입니다.",
    3102: "존재하지 않는 창고 입니다.",
    3103: "처분할 상품이 존재하지 않습니다.",
  };

  // 에러 코드에 해당하는 메시지가 있으면 그 메시지를, 없으면 기본 메시지를 반환
  return errorMessages[errorCode] || "알 수 없는 오류가 발생했습니다.";
}
