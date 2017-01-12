package org.inverse.query;

import net.sf.jsqlparser.expression.Expression;

/**
 */
public interface ValueFactory {
    Value inferValue(Expression expression);
}
