package org.inverse.service;

import org.inverse.exception.QueryNotSupportedException;
import org.inverse.exception.QueryParsingException;
import org.inverse.query.Query;

/**
 */
public interface ParserService {
    Query parse(String sql) throws QueryNotSupportedException, QueryParsingException;
}
