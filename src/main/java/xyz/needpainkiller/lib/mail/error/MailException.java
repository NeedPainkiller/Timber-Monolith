package xyz.needpainkiller.lib.mail.error;

import xyz.needpainkiller.lib.exceptions.BusinessException;
import xyz.needpainkiller.lib.exceptions.ErrorCode;

import java.util.Map;

public class MailException extends BusinessException {

    public MailException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MailException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public MailException(ErrorCode errorCode, Map<String, Object> model) {
        super(errorCode, model);
    }

    public MailException(ErrorCode errorCode, String message, Map<String, Object> model) {
        super(errorCode, message, model);
    }
}
