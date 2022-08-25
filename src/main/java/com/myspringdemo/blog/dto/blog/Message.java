package com.myspringdemo.blog.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private String message;
    private boolean hasMessage;
    private String type;



}
