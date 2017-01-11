package org.inverse.service;

/**
 * Created by Dell on 11-01-2017.
 */
public interface SchemaService {
    /**
     * Returns schema of the given table
     *
     * @param tableName name of the database table
     * @return schema
     */
    String getSchema(String tableName);
}
