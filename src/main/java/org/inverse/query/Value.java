package org.inverse.query;

/**
 * Represents a single column value of any type stored in the database
 *
 * @author anurag
 * @since 1.2
 */
public interface Value {
    ValueType getType();

    String getValue();
}
