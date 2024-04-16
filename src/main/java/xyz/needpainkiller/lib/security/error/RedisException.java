package xyz.needpainkiller.lib.security.error;

import xyz.needpainkiller.lib.exceptions.BusinessException;
import xyz.needpainkiller.lib.exceptions.ErrorCode;

import java.util.Map;

public class RedisException extends BusinessException {

    public RedisException(ErrorCode errorCode) {
        super(errorCode);
    }

    public RedisException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public RedisException(ErrorCode errorCode, Map<String, Object> model) {
        super(errorCode, model);
    }

    public RedisException(ErrorCode errorCode, String message, Map<String, Object> model) {
        super(errorCode, message, model);
    }
}
