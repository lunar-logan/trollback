package org.inverse.service.impl;

import org.inverse.exception.QueryNotSupportedException;
import org.inverse.exception.QueryParsingException;
import org.inverse.query.Query;
import org.inverse.service.ParserService;
import org.inverse.service.QueryInverterService;
import org.inverse.service.RollbackService;
import org.inverse.service.SchemaService;

/**
 */
public class RollbackServiceImpl implements RollbackService {
    private final SchemaService schemaService;
    private final ParserService parserService;
    private final QueryInverterService queryInverterService;

    public RollbackServiceImpl(SchemaService schemaService, ParserService parserService, QueryInverterService queryInverterService) {
        this.schemaService = schemaService;
        this.parserService = parserService;
        this.queryInverterService = queryInverterService;
    }

    public String getRollbackQuery(String sql) throws QueryParsingException, QueryNotSupportedException {
        // First we parse the query
        Query parsedSql = parserService.parse(sql);

        // Second get the schema
        String schema = schemaService.getSchema(parsedSql.getTableName());

        // Third invert the query to get the rollback
        return queryInverterService.invert(parsedSql, schema);
    }
}
