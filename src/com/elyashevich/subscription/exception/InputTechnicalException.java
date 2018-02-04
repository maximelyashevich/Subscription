package com.elyashevich.subscription.exception;

public class InputTechnicalException extends Exception {
    public InputTechnicalException() {
    }

    public InputTechnicalException(String message, Throwable exception) {
        super(message, exception);
    }

    public InputTechnicalException(String message) {
        super(message);
    }

    public InputTechnicalException(Throwable exception) {
        super(exception);
    }

}
