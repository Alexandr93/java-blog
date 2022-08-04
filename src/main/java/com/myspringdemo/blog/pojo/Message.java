package com.myspringdemo.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private String message;
    private boolean hasMessage;
    private String type;

}
