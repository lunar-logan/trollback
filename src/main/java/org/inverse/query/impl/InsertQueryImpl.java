package org.inverse.query.impl;

import org.inverse.query.InsertQuery;

import java.util.List;

/**
 * Created by Dell on 12-01-2017.
 */
public class InsertQueryImpl implements InsertQuery {
    private final String tableName;
    private final List<String> columns;
    private final List<String> values;

    public InsertQueryImpl(String tableName, List<String> columns, List<String> values) {
        this.tableName = tableName;
        this.columns = columns;
        this.values = values;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public List<String> getValues() {
        return values;
    }

    @Override
    public List<String> getColumnNames() {
        return columns;
    }
}
