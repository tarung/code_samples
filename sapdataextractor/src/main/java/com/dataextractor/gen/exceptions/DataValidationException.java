package com.dataextractor.gen.exceptions;

public class DataValidationException extends Exception {

	private static final long serialVersionUID = 1L;

    protected Throwable throwable;


    public DataValidationException(final String message) {
        super(message);
    }

    public DataValidationException(final String message, final Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    @Override
    public synchronized Throwable getCause() {
        return throwable;
    }

}
