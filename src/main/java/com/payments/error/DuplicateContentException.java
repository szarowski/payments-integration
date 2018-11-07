package com.payments.error;

public class DuplicateContentException extends RuntimeException {

    public DuplicateContentException(final String message) {
        super(message);
    }
}
