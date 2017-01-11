package org.inverse.exception;

/**
 * Created by Dell on 11-01-2017.
 */
public class QueryParsingException extends Exception {
    public QueryParsingException() {
    }

    public QueryParsingException(String message) {
        super(message);
    }

    public QueryParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
