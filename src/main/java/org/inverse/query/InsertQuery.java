package org.inverse.query;

import java.util.List;

/**
 * Created by Dell on 11-01-2017.
 */
public interface InsertQuery extends Query {
    List<String> getValues();

    List<String> getColumnNames();
}
