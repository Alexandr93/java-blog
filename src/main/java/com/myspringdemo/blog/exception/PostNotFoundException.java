package com.myspringdemo.blog.exception;

public class PostNotFoundException extends Exception{
    public PostNotFoundException(String message) {
        super(message);
    }
}
