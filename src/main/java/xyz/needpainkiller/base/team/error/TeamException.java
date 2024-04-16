package xyz.needpainkiller.base.team.error;

import xyz.needpainkiller.lib.exceptions.BusinessException;
import xyz.needpainkiller.lib.exceptions.ErrorCode;

import java.util.Map;

public class TeamException extends BusinessException {

    public TeamException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TeamException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public TeamException(ErrorCode errorCode, Map<String, Object> model) {
        super(errorCode, model);
    }

    public TeamException(ErrorCode errorCode, String message, Map<String, Object> model) {
        super(errorCode, message, model);
    }
}
