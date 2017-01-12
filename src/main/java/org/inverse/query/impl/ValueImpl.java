package org.inverse.query.impl;

import net.sf.jsqlparser.expression.*;
import org.inverse.query.Value;
import org.inverse.query.ValueType;

/**
 * @author anurag
 */
public class ValueImpl implements Value {
    private final ValueType type;
    private final String value;

    public ValueImpl(final Expression expression) {
        if (expression instanceof StringValue) {
            type = ValueType.STRING;
            value = ((StringValue) expression).getValue();
        } else if (expression instanceof LongValue) {
            type = ValueType.LONG;
            value = String.valueOf(((LongValue) expression).getValue());
        } else if (expression instanceof DoubleValue) {
            type = ValueType.DOUBLE;
            value = String.valueOf(((DoubleValue) expression).getValue());
        } else if (expression instanceof DateValue) {
            type = ValueType.DATE;
            value = String.valueOf(((DateValue) expression).getValue());
        } else if (expression instanceof TimeValue) {
            type = ValueType.TIME;
            value = String.valueOf(((TimeValue) expression).getValue());
        } else if (expression instanceof TimestampValue) {
            type = ValueType.TIMESTAMP;
            value = String.valueOf(((TimestampValue) expression).getValue());
        } else if (expression instanceof NullValue) {
            type = ValueType.NULL;
            value = "" + null;
        } else {
            type = ValueType.UNKNOWN;
            value = String.valueOf(expression.toString());
        }
    }

    @Override
    public ValueType getType() {
        return type;
    }

    @Override
    public String getValue() {
        return value;
    }
}
