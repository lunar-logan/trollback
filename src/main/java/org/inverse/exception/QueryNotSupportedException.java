package org.inverse.exception;

/**
 * Created by Dell on 11-01-2017.
 */
public class QueryNotSupportedException extends Exception {
    public QueryNotSupportedException() {
    }

    public QueryNotSupportedException(String message) {
        super(message);
    }

    public QueryNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
