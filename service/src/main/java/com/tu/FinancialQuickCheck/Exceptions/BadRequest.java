package com.tu.FinancialQuickCheck.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown when input is incorrect/missing or information is insufficient
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException {

    public BadRequest(String exception){
            super(exception);
        }

}

