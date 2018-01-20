package com.elyashevich.subscription.exception;

public class ReceiverTechnicalException extends Exception {
    public ReceiverTechnicalException() {
    }

    public ReceiverTechnicalException(String message, Throwable exception) {
        super(message, exception);
    }

    public ReceiverTechnicalException(String message) {
        super(message);
    }

    public ReceiverTechnicalException(Throwable exception) {
        super(exception);
    }
}
