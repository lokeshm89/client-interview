package com.adp.coindispenser.exception;

public class ApplicationBusyException extends RuntimeException {

    public ApplicationBusyException() {
        super("Application is busy, please try again.");
    }
}
