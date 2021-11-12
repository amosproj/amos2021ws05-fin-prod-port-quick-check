package com.tu.FinancialQuickCheck.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFound extends RuntimeException {

    public ProjectNotFound(String exception){
        super(exception);
    }
}