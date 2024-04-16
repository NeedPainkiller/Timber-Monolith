package xyz.needpainkiller.base.file.error;

import org.springframework.http.HttpStatus;
import xyz.needpainkiller.lib.exceptions.ErrorCode;


public enum FileErrorCode implements ErrorCode {

    FILE_STORAGE_CONTEXT_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "파일 서비스에서 사용하는 서블릿 컨택스트가 확인되지 않음"),
    FILE_STORAGE_USABLE_SIZE_LIMIT(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장소 크기가 충분하지 않거나 확인되지 않음"),
    FILE_RESTRICT_EXTENSION(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 확장자"),
    FILE_INVALID_NAME_FAILED(HttpStatus.BAD_REQUEST, "파일 이름 이나 확장자가 확인되지 않음"),
    FILE_UPLOAD_IS_NOT_MULTIPART(HttpStatus.INTERNAL_SERVER_ERROR, "multipart 업로드 형식이 아님"),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패함"),
    FILE_UPLOAD_SIZE_LIMIT(HttpStatus.BAD_REQUEST, "파일 크기가 제한됨"),
    FILE_UPLOAD_INFO_NOT_EXIST(HttpStatus.NOT_FOUND, "파일 메타 정보가 확인되지 않은 파일"),
    FILE_UPLOAD_INFO_ALREADY_EXIST(HttpStatus.NOT_FOUND, "이미 서버에 등록된 파일은 재등록 할 수 없음"),

    FILE_DOWNLOAD_CAN_NOT_USABLE(HttpStatus.NOT_FOUND, "사용이 불가능한 파일"),

    FILE_DOWNLOAD_FILE_INFO_NOT_EXIST(HttpStatus.NOT_FOUND, "등록되지 않은 파일"),

    FILE_DOWNLOAD_FILE_NOT_EXIST(HttpStatus.NOT_FOUND, "파일서버에 존재하지 않는 파일"),
    FILE_DOWNLOAD_FAILED(HttpStatus.NOT_FOUND, "서버에 등록되지 않은 파일이거나, 파일서버에서 파일을 확인할 수 없음"),
    FILE_DOWNLOAD_FILE_NOT_YOU_OWN(HttpStatus.FORBIDDEN, "해당 파일에 대한 접근권한이 없습니다"),
    FILE_DOWNLOAD_FILE_NEED_AUTH(HttpStatus.UNAUTHORIZED, "해당 파일은 인증된 유저만 조회가 가능합니다"),
    FILE_DOWNLOAD_FILE_EMPTY_AUTH(HttpStatus.BAD_REQUEST, "해당 파일의 접근권한이 책정되지 않았습니다"),
    FILE_DOWNLOAD_NOT_SCRIPT_FILE(HttpStatus.BAD_REQUEST, "스크립트 파일이 아님"),
    FILE_SECURE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 암호화 업로드 실패함"),
    FILE_SECURE_DOWNLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 복호화 다운로드 실패함"),

    FILE_ENCRYPT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 암호화 실패함"),
    FILE_DECRYPT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 복호화 실패함"),

    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제 실패함"),

    FILE_CSV_PARSE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CSV 파일 변환에 실패함");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    FileErrorCode(HttpStatus status, String errorMessage) {
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


