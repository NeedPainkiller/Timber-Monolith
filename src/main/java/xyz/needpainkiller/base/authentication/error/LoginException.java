package xyz.needpainkiller.base.authentication.error;

import xyz.needpainkiller.lib.exceptions.BusinessException;
import xyz.needpainkiller.lib.exceptions.ErrorCode;

import java.util.Map;

public class LoginException extends BusinessException {

    public LoginException(ErrorCode errorCode) {
        super(errorCode);
    }

    public LoginException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public LoginException(ErrorCode errorCode, Map<String, Object> model) {
        super(errorCode, model);
    }

    public LoginException(ErrorCode errorCode, String message, Map<String, Object> model) {
        super(errorCode, message, model);
    }
}
