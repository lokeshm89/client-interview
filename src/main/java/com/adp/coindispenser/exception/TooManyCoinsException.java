package com.adp.coindispenser.exception;

public class TooManyCoinsException extends RuntimeException {


    public TooManyCoinsException(Integer maxCoins, double totalCoinsRequiredToCompleteTransaction) {
        super(totalCoinsRequiredToCompleteTransaction + " coins required to completed transaction. " +
                "This is higher than user's allowed maximum coin count of " + maxCoins);
    }
}
