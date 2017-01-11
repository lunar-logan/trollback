package org.inverse.service.impl;

import org.inverse.exception.QueryNotSupportedException;
import org.inverse.exception.QueryParsingException;
import org.inverse.query.InsertQuery;
import org.inverse.query.Query;
import org.inverse.query.CreateQuery;
import org.inverse.service.ParserService;
import org.inverse.service.QueryInverterService;
import org.inverse.util.CollectionUtils;

import java.util.*;

/**
 * Created by Dell on 11-01-2017.
 */
public class QueryInverterServiceImpl implements QueryInverterService {
    private final ParserService parserService;

    public QueryInverterServiceImpl(ParserService parserService) {
        this.parserService = parserService;
    }

    public String invert(Query query, String schemaSql) throws QueryParsingException, QueryNotSupportedException {
        CreateQuery schema = (CreateQuery) parserService.parse(schemaSql);
        if (query instanceof InsertQuery) {
            return rollbackInsert((InsertQuery) query, schema);
        }
        throw new QueryNotSupportedException();
    }

    private String rollbackInsert(InsertQuery query, CreateQuery schema) {
        Map<String, String> valueMap;

        List<String> columns = schema.getColumns();
        List<String> values = query.getValues();
        if (columns.size() == values.size()) {
            valueMap = CollectionUtils.toHashMap(columns, values);
        } else {
            List<String> columnsSpecifiedInQuery = query.getColumnNames();
            if (columnsSpecifiedInQuery.size() == values.size()) {
                valueMap = CollectionUtils.toHashMap(columnsSpecifiedInQuery, values);
            } else {
                throw new IllegalStateException("Syntax error in the SQL");
            }
        }

        Set<String> indices = new HashSet<>();
        indices.addAll(schema.getIndices());

        List<String> criterion = new ArrayList<>();
        valueMap.forEach((k, v) -> {
            if (indices.contains(k)) {
                criterion.add(k + "=" + v);
            }
        });
        StringJoiner joiner = new StringJoiner(" AND ");
        criterion.forEach(joiner::add);
        return "DELETE FROM " + query.getTableName() + " " +
                "WHERE " + joiner.toString() + ";";
    }

}
