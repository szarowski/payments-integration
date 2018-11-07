package com.payments.error;

import com.payments.error.model.RequestError;

import java.util.List;

import static java.util.Arrays.asList;

public class UnprocessableEntityException extends RuntimeException {

    private final List<RequestError> errors;

    public UnprocessableEntityException(final RequestError... errors) {
        super("Validation errors");
        this.errors = asList(errors);
    }

    public List<RequestError> getErrors() {
        return errors;
    }
}
