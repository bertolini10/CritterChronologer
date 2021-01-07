package com.udacity.jdnd.course3.critter.ex;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyExecption extends RuntimeException{

    public MyExecption(String message) {
        super(message);
    }


}