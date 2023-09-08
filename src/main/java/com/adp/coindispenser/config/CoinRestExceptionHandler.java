package com.adp.coindispenser.config;

import com.adp.coindispenser.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CoinRestExceptionHandler {

    @ExceptionHandler(UnSupportedBillException.class)
    public ResponseEntity<String> handleUnSupportedBillException(
            UnSupportedBillException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnSupportedApiVersion.class)
    public ResponseEntity<String> handleUnSupportedApiVersion(
            UnSupportedApiVersion ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(
            AccessDeniedException ex) {
        return new ResponseEntity<>("This user cannot perform this action", HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(NotEnoughCoinsException.class)
    public ResponseEntity<String> handleNotEnoughCoinsException(
            NotEnoughCoinsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(TooManyCoinsException.class)
    public ResponseEntity<String> handleTooManyCoinsException(
            TooManyCoinsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ApplicationBusyException.class)
    public ResponseEntity<String> handleTooManyCoinsException(
            ApplicationBusyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
    }
}
