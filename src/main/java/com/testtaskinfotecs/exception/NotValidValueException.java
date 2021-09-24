package com.testtaskinfotecs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotValidValueException extends RuntimeException{

    public NotValidValueException(String message){
        super(message);
    }
}
