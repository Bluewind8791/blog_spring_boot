package com.cos.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplySaveRequestDto {
    
    private Long userId;
    private Long boardId;
    private String content;
}
