package xyz.needpainkiller.api.workingday.error;

import xyz.needpainkiller.lib.exceptions.BusinessException;
import xyz.needpainkiller.lib.exceptions.ErrorCode;

import java.util.Map;

public class WorkingDayException extends BusinessException {

    public WorkingDayException(ErrorCode errorCode) {
        super(errorCode);
    }

    public WorkingDayException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public WorkingDayException(ErrorCode errorCode, Map<String, Object> model) {
        super(errorCode, model);
    }

    public WorkingDayException(ErrorCode errorCode, String message, Map<String, Object> model) {
        super(errorCode, message, model);
    }
}
