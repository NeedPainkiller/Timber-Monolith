package xyz.needpainkiller.schedule.error;

import xyz.needpainkiller.lib.exceptions.BusinessException;
import xyz.needpainkiller.lib.exceptions.ErrorCode;

import java.util.Map;

public class QuartzSchedulerException extends BusinessException {

    public QuartzSchedulerException(ErrorCode errorCode) {
        super(errorCode);
    }

    public QuartzSchedulerException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public QuartzSchedulerException(ErrorCode errorCode, Map<String, Object> model) {
        super(errorCode, model);
    }

    public QuartzSchedulerException(ErrorCode errorCode, String message, Map<String, Object> model) {
        super(errorCode, message, model);
    }
}
