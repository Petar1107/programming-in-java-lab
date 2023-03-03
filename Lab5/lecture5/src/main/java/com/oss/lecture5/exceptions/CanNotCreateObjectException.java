package com.oss.lecture5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CanNotCreateObjectException extends Exception {
    public CanNotCreateObjectException(String message){
        super(message);
    }

    public CanNotCreateObjectException(String message, Throwable cause){
        super(message,cause);
    }
}
