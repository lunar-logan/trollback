package org.inverse.query.impl;

import org.inverse.query.CreateQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dell on 12-01-2017.
 */
public class CreateQueryImpl implements CreateQuery {
    private final String tableName;
    private final ArrayList<String> columns;
    private final ArrayList<String> indices;

    public CreateQueryImpl(String table, Collection<String> columns, Collection<String> indices) {
        this.tableName = table;
        this.columns = new ArrayList<>();
        this.columns.addAll(columns);

        this.indices = new ArrayList<>();
        this.indices.addAll(indices);
    }


    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public List<String> getIndices() {
        return indices;
    }

    @Override
    public List<String> getColumns() {
        return columns;
    }
}
