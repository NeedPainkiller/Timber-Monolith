package xyz.needpainkiller.api.authentication.error;

import xyz.needpainkiller.lib.exceptions.BusinessException;
import xyz.needpainkiller.lib.exceptions.ErrorCode;

import java.util.Map;

public class ApiException extends BusinessException {

    public ApiException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ApiException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ApiException(ErrorCode errorCode, Map<String, Object> model) {
        super(errorCode, model);
    }

    public ApiException(ErrorCode errorCode, String message, Map<String, Object> model) {
        super(errorCode, message, model);
    }
}
