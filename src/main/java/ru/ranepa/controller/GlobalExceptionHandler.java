package ru.ranepa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleException(MethodArgumentNotValidException exception) {
        Set<String> errorsAsText = new HashSet<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            errorsAsText.add(error.getDefaultMessage());
        }

        StringBuilder errorsDescription = new StringBuilder();
        for (String errorText: errorsAsText){
            errorsDescription.append(errorText).append('\n');
        }
        return errorsDescription.toString();
    }
}
