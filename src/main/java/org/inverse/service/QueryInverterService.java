package org.inverse.service;

import org.inverse.exception.QueryNotSupportedException;
import org.inverse.exception.QueryParsingException;
import org.inverse.query.Query;

/**
 * Created by Dell on 11-01-2017.
 */
public interface QueryInverterService {
    String invert(Query query, String schema) throws QueryParsingException, QueryNotSupportedException;
}
