package com.adp.coindispenser.exception;

import org.springframework.http.HttpStatus;

public class UnSupportedBillException extends RuntimeException {
    public UnSupportedBillException(String s) {
        super(s);
    }
}
