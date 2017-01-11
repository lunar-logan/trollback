package org.inverse.query;

import java.util.List;

/**
 * Created by Dell on 11-01-2017.
 */
public interface CreateQuery extends Query {
    List<String> getIndices();

    List<String> getColumns();
}
