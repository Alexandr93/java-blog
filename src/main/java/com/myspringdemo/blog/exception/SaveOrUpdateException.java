package com.myspringdemo.blog.exception;

public class SaveOrUpdateException extends RuntimeException{
    public SaveOrUpdateException(String message) {
        super(message);
    }
}
