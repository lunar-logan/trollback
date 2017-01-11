package org.inverse.service;

import org.inverse.exception.QueryNotSupportedException;
import org.inverse.exception.QueryParsingException;

/**
 */
public interface RollbackService {
    String getRollbackQuery(String query) throws QueryParsingException, QueryNotSupportedException;
}
