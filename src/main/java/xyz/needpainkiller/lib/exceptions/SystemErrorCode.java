package xyz.needpainkiller.lib.exceptions;

import org.springframework.http.HttpStatus;


public enum SystemErrorCode implements ErrorCode {

    EMPLOYEE_NOT_EXIST(HttpStatus.NOT_FOUND, "사원정보가 확인되지 않습니다"),
    EMPLOYEE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "해당 사번으로 등록된 계정이 이미 존재합니다"),
    EMPLOYEE_SUBSCRIBE_NOT_EXIST(HttpStatus.NOT_FOUND, " 계정 신청 정보를 확인 할 수 없습니다"),
    EMPLOYEE_SUBSCRIBE_ALREADY_ACCEPTED_OK(HttpStatus.BAD_REQUEST, "이미 승인된 계정 신청은 수정할 수 없습니다"),

    ETC_CODE_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 코드 정보입니다"),
    ETC_CODE_ROW_EMPTY(HttpStatus.NOT_FOUND, "상세코드 정보가 확인되지 않습니다"),

    HISTORY_SEARCH_LIST_EMPTY(HttpStatus.NOT_FOUND, "검색조건에 맞는 기록 데이터가 조회되지 않음"),

    DW_SYNC_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "DataWare 동기화에 실패함"),

    BANNER_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 배너"),
    BANNER_SAVE_FAILED(HttpStatus.NOT_FOUND, "배너 삭제에 실패함"),

    TERMS_TYPE_NOT_EXIST(HttpStatus.NOT_FOUND, "조건에 맞는 약관이 조회되지 않음"),
    TERMS_NO_CONTENT(HttpStatus.NOT_FOUND, "약관이 조회되지 않음"),
    TERMS_UPDATE_UNAUTHORIZED(HttpStatus.FORBIDDEN, "약관 갱신권한이 확인되지 않음"),
    TERMS_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "약관 갱신 실패"),

    PAYMENT_SIGNATURE_CREATE_FAILED(HttpStatus.BAD_REQUEST, "결제 시그니쳐 생성 실패"),
    PAYMENT_SIGN_HASH_CREATE_FAILED(HttpStatus.BAD_REQUEST, "가맹점 확인용 해시 생성 실패"),

    PAYMENT_SIGNATURE_NOT_MATCH(HttpStatus.BAD_REQUEST, "데이터 위변조 체크 실패"),
    PAYMENT_RESULT_CODE_MISSING(HttpStatus.BAD_REQUEST, "결제처리 resultCode 누락"),
    PAYMENT_REQUEST_PARAM_MISSING(HttpStatus.BAD_REQUEST, "파라미터 누락, 규칙에 맞지 않는 파라미터"),
    PAYMENT_REQUEST_NEED_RETRY(HttpStatus.BAD_REQUEST, "승인처리 불가"),
    PAYMENT_REQUEST_INTERNAL_ERROR(HttpStatus.BAD_REQUEST, "결제 처리 중 결제대행사 이슈 발생"),
    PAYMENT_REQUEST_ENCODING_UNSUPPORTED(HttpStatus.BAD_REQUEST, "인증정보 요청이 UTF-8 인코딩을 지원하지 않음"),
    PAYMENT_REQUEST_AUTHENTICATION_FAILED(HttpStatus.BAD_REQUEST, "결제대행사의 본인인증 실패"),
    PAYMENT_CANCEL_FAILED(HttpStatus.BAD_REQUEST, "결제 중도취소 실패"),
    PAYMENT_REQUEST_REFUND_FAILED(HttpStatus.BAD_REQUEST, "환불요청 실패"),
    PAYMENT_HASH_REFUND_FAILED(HttpStatus.BAD_REQUEST, "환불정보 해시처리 실패"),

    PAYMENT_HISTORY_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 결제 기록"),
    PAYMENT_HISTORY_SAVE_FAILED(HttpStatus.BAD_REQUEST, "존재하지 않는 저장 / 업데이트 실패"),


    REDIS_CONNECTION_TIMEOUT(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS 서버 응답시간 초과"),
    REDIS_CONNECTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS 서버 응답 실패"),

    SETTING_NOT_EXIST(HttpStatus.NOT_FOUND, "설정하고자 하는 값의 키가 존재하지 않음"),
    SETTING_WRITE_VALUE_TYPE_ERROR(HttpStatus.BAD_REQUEST, "설정하고자 하는 값의 타입이 맞지 않거나 예상하지 못한 데이터가 기입됨");


    private final HttpStatus httpStatus;
    private final String errorMessage;

    SystemErrorCode(HttpStatus status, String errorMessage) {
        this.httpStatus = status;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return name();
    }
}




