package com.cos.blog.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    
    int status;
    T data;
}
