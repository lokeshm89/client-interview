package com.adp.coindispenser.exception;

public class UnSupportedApiVersion extends Throwable {
    public UnSupportedApiVersion(String version) {
        super("Invalid API version " + version);
    }
}
