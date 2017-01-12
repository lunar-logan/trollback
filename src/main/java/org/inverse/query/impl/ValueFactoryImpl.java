package org.inverse.query.impl;

import net.sf.jsqlparser.expression.Expression;
import org.inverse.query.Value;
import org.inverse.query.ValueFactory;

/**
 * @author Mr. MoST
 */
public class ValueFactoryImpl implements ValueFactory {
    @Override
    public Value inferValue(Expression expression) {
        return new ValueImpl(expression);
    }
}
