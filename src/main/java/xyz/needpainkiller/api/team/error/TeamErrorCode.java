package xyz.needpainkiller.api.team.error;

import org.springframework.http.HttpStatus;
import xyz.needpainkiller.lib.exceptions.ErrorCode;


public enum TeamErrorCode implements ErrorCode {
    TEAM_ROOT_NOT_EXIST(HttpStatus.NOT_FOUND, "최초 부서정보가 기입되지 않았습니다"),
    TEAM_NOT_EXIST(HttpStatus.NOT_FOUND, "부서를 확인 할 수 없습니다"),
    TEAM_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 부서"),
    TEAM_USER_REQUEST_EMPTY(HttpStatus.BAD_REQUEST, "부서에 배정할 인원이 확인되지 않습니다"),

    TEAM_PARENT_REQUEST_EMPTY(HttpStatus.BAD_REQUEST, "상위 부서 정보가 누락되어 있습니다"),
    TEAM_PARENT_NOT_EXIST(HttpStatus.NOT_FOUND, "상위 부서 정보가 확인되지 않습니다"),
    TEAM_PARENT_CIRCULAR_REFERENCES(HttpStatus.NOT_FOUND, "사용 할 수 없는  상위 부서 입니다"),
    TEAM_CHILDREN_EXIST(HttpStatus.BAD_REQUEST, "하위 부서가 존재하여 수정할 수 없습니다. 하위 팀을 제거해 주세요");
    private final HttpStatus httpStatus;
    private final String errorMessage;

    TeamErrorCode(HttpStatus status, String errorMessage) {
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



