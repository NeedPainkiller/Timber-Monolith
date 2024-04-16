package xyz.needpainkiller.base.workingday.error;

import org.springframework.http.HttpStatus;
import xyz.needpainkiller.lib.exceptions.ErrorCode;


public enum WorkingDayErrorCode implements ErrorCode {

    ORG_WORKING_DAY_ALREADY_PASS(HttpStatus.BAD_REQUEST, "만료된 일자의 휴일은 등록되거나 제거 될 수 없습니다."),
    ORG_WORKING_DAY_WRONG_DATE_RANGE(HttpStatus.BAD_REQUEST, "시작-종료일자가 유효하지 않습니다."),
    ORG_WORKING_DAY_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다.");


    private final HttpStatus httpStatus;
    private final String errorMessage;

    WorkingDayErrorCode(HttpStatus status, String errorMessage) {
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


