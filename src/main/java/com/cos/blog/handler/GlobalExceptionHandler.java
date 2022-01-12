package com.cos.blog.handler;

import com.cos.blog.dto.ResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ControllerAdvice // exception이 발생하면 이 클래스로 들어옴
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = Exception.class)
    public ResponseDto<String> ArgumentExceptionHandler(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()); // 500 error message
    }

    // @ExceptionHandler(value = IllegalArgumentException.class)
    // public String handleArgumentException(IllegalArgumentException e) {
    //     return "<h1>"+e.getMessage()+"</h1>";
    // }

    // @ExceptionHandler(value = EmptyResultDataAccessException.class)
    // public String emptyResultDataAccessExceptionHandler(EmptyResultDataAccessException e) {
    //     String msg = "존재하지 않는 사용자입니다.\n"+"<h1>"+e.getMessage()+"</h1>";
    //     return msg;
    // }
}
