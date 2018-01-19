package com.elyashevich.subscription.exception;

public class ServiceTechnicalException extends Exception {
    public ServiceTechnicalException() {
    }

    public ServiceTechnicalException(String message, Throwable exception) {
        super(message, exception);
    }

    public ServiceTechnicalException(String message) {
        super(message);
    }

    public ServiceTechnicalException(Throwable exception) {
        super(exception);
    }
}
