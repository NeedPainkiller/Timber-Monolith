package xyz.needpainkiller.base.authentication.error;

import xyz.needpainkiller.lib.exceptions.BusinessException;
import xyz.needpainkiller.lib.exceptions.ErrorCode;

import java.util.Map;

public class PasswordException extends BusinessException {

    public PasswordException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PasswordException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public PasswordException(ErrorCode errorCode, Map<String, Object> model) {
        super(errorCode, model);
    }

    public PasswordException(ErrorCode errorCode, String message, Map<String, Object> model) {
        super(errorCode, message, model);
    }
}
