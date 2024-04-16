package xyz.needpainkiller.lib.exceptions;

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static xyz.needpainkiller.api.file.error.FileErrorCode.FILE_INVALID_NAME_FAILED;


@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServiceExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException e) {
        log.error("handleBusinessException", e);
        ApiErrorResponse response = ApiErrorResponse.of(e.getErrorCode(), e);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(InvalidFileNameException.class)
    protected ResponseEntity<ApiErrorResponse> handleInvalidFileNameException(InvalidFileNameException e) {
        log.info("handleStorageException", e);
        ApiErrorResponse response = ApiErrorResponse.of(FILE_INVALID_NAME_FAILED, e);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(RedisException.class)
    protected ResponseEntity<ApiErrorResponse> handleRedisException(RedisException e) {
        log.info("handleRedisException", e);
        ApiErrorResponse response = ApiErrorResponse.of(ServiceErrorCode.REDIS_CONNECTION_FAILED, e);
        return new ResponseEntity<>(response, response.getStatus());
    }


}