package com.myspringdemo.blog.exception;


import com.myspringdemo.blog.rest.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Response> handleException(UsernameNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(PostNotFoundException.class)
    public  ResponseEntity<Response> handlePostNotFoundException(PostNotFoundException e){
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(SaveOrUpdateException.class)
    public  ResponseEntity<Response> handlePostNotFoundException(SaveOrUpdateException e){
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

}
