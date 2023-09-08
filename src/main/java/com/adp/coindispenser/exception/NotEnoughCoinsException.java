package com.adp.coindispenser.exception;

public class NotEnoughCoinsException extends RuntimeException {
    public NotEnoughCoinsException(Integer bill) {
        super("Not enough coins. Request cannot be processed");
    }
}
