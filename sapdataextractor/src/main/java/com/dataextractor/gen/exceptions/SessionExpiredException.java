package com.dataextractor.gen.exceptions;

public class SessionExpiredException extends Exception {

	private static final long serialVersionUID = 1L;

    protected Throwable throwable;


    public SessionExpiredException(final String message) {
        super(message);
    }

    public SessionExpiredException(final String message, final Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    @Override
    public synchronized Throwable getCause() {
        return throwable;
    }

}
