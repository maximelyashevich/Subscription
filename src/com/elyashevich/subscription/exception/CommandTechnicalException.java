package com.elyashevich.subscription.exception;

public class CommandTechnicalException extends Exception {
    public CommandTechnicalException() {
    }

    public CommandTechnicalException(String message, Throwable exception) {
        super(message, exception);
    }

    public CommandTechnicalException(String message) {
        super(message);
    }

    public CommandTechnicalException(Throwable exception) {
        super(exception);
    }

}
