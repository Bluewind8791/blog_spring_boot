package com.cos.blog.handler;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@ControllerAdvice // exception이 발생하면 이 클래스로 들어옴
@RestController
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = Exception.class)
    public String globalExceptionHandler(Exception e) {
        return "<h1>"+e.getMessage()+"</h1>";
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public String handleArgumentException(IllegalArgumentException e) {
        return "<h1>"+e.getMessage()+"</h1>";
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public String emptyResultDataAccessExceptionHandler(EmptyResultDataAccessException e) {
        String msg = "존재하지 않는 사용자입니다.\n"+"<h1>"+e.getMessage()+"</h1>";

        
        return msg;
    }
}
