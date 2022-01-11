package com.tu.FinancialQuickCheck.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown when resources (e.g. IDs or users) are not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String exception){
        super(exception);
    }
}