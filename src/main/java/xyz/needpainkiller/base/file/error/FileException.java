package xyz.needpainkiller.base.file.error;

import xyz.needpainkiller.lib.exceptions.BusinessException;
import xyz.needpainkiller.lib.exceptions.ErrorCode;

import java.util.Map;

public class FileException extends BusinessException {

    public FileException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FileException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public FileException(ErrorCode errorCode, Map<String, Object> model) {
        super(errorCode, model);
    }

    public FileException(ErrorCode errorCode, String message, Map<String, Object> model) {
        super(errorCode, message, model);
    }
}
