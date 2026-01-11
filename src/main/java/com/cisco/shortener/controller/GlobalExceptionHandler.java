package com.cisco.shortener.controller;

import com.cisco.shortener.exception.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice // Tells Spring this class watches for errors across all controllers
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class) // Catch this specific exception
    @ResponseStatus(HttpStatus.NOT_FOUND) // Return a 404 status code to the user
    @ResponseBody
    public String handleUrlNotFound(UrlNotFoundException e) {
        // This is the message that will appear in the browser/Postman
        return e.getMessage();
    }
}