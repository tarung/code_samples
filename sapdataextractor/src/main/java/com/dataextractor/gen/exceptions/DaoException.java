package com.dataextractor.gen.exceptions;

public class DaoException extends Exception {
    /**  */
    private static final long serialVersionUID = 1L;

    protected Throwable throwable;

    /**
     * Method 'DaoException'
     *
     * @param message
     */
    public DaoException(final String message) {
        super(message);
    }

    /**
     * Method 'DaoException'
     *
     * @param message
     * @param throwable
     */
    public DaoException(final String message, final Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    /**
     * Method 'getCause'
     *
     * @return Throwable
     */
    @Override
    public synchronized Throwable getCause() {
        return throwable;
    }

}
