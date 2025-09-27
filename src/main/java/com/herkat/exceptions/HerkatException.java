package com.herkat.exceptions;

public class HerkatException extends RuntimeException {

    private final ErrorMessage error;

    public HerkatException(ErrorMessage error) {
        super(error.getMessage());
        this.error = error;
    }

    public ErrorMessage getError() {
        return error;
    }

}
