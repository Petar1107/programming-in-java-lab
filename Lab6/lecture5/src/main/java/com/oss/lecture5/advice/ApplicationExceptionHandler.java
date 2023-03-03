package com.oss.lecture5.advice;

import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> handleGetObjectException(NotFoundException ex){
        var date = new Date();
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        errorMap.put("statusCode", HttpStatus.NOT_FOUND.toString());
        errorMap.put("timestamp", date.toString());
        ex.printStackTrace();
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CanNotCreateObjectException.class)
    public Map<String, String> handleCreateClientException(CanNotCreateObjectException ex){
        var date = new Date();
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        errorMap.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorMap.put("timestamp", date.toString());
        ex.printStackTrace();
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleCreateClientException(MethodArgumentTypeMismatchException ex){
        var date = new Date();
        Map<String, String> errorMap = new HashMap<>();
        if (ex.getRootCause() != null) {
            errorMap.put("errorMessage", ex.getRootCause().getMessage());
        }
        errorMap.put("errorMessage", ex.getMessage());
        errorMap.put("statusCode", HttpStatus.BAD_REQUEST.toString());
        errorMap.put("timestamp", date.toString());
        ex.printStackTrace();
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String, String> handleCreateClientException(MissingServletRequestParameterException ex){
        var date = new Date();
        Map<String, String> errorMap = new HashMap<>();
        if (ex.getRootCause() != null) {
            errorMap.put("errorMessage", ex.getRootCause().getMessage());
        }
        errorMap.put("errorMessage", ex.getMessage());
        errorMap.put("statusCode", HttpStatus.BAD_REQUEST.toString());
        errorMap.put("timestamp", date.toString());
        ex.printStackTrace();
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleCreateClientException(IllegalArgumentException ex){
        var date = new Date();
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        errorMap.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorMap.put("timestamp", date.toString());
        ex.printStackTrace();
        return errorMap;
    }
}
