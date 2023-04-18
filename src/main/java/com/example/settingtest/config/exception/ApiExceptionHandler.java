package com.example.settingtest.config.exception;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

	private Logger log = LogManager.getLogger(this.getClass());

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ApiExceptionInfo> handleMethodArgumentNotValidException(ApiException e) {
        log.error("API Exception : {}", e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            log.error("{}", element.toString());
        }
        final ApiExceptionInfo response = new ApiExceptionInfo();
        response.setHttpStatus(e.getStatusCode());
        response.setMessage(e.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, e.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiExceptionInfo> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
    	log.error("API Exception : {}", e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
        	log.error("{}", element.toString());
        }
        final ApiExceptionInfo response = new ApiExceptionInfo();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiExceptionInfo> handleMethodArgumentNotValidException(Exception e) {
        log.error("API Exception : {}", e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
        	log.error("{}", element.toString());
        }
        final ApiExceptionInfo response = new ApiExceptionInfo();
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(e.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
