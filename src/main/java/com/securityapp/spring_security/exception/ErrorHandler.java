package com.securityapp.spring_security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,Object>>userNotFound(UserNotFoundException exception){
        Map<String,Object>errorMap=new HashMap<>();
        errorMap.put("Status","Error");
        errorMap.put("Message",exception.getMessage());
        errorMap.put("Code", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorMap,HttpStatus.NOT_FOUND);
    }
}
