package xyz.needpainkiller.lib.exceptions;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMessage();

    HttpStatus getStatus();

    String getCode();


}
