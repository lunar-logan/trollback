package org.inverse.service.impl;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import net.sf.jsqlparser.statement.insert.Insert;
import org.inverse.exception.QueryNotSupportedException;
import org.inverse.exception.QueryParsingException;
import org.inverse.query.Query;
import org.inverse.query.QueryFactory;
import org.inverse.query.Value;
import org.inverse.query.ValueFactory;
import org.inverse.query.impl.CreateQueryImpl;
import org.inverse.service.ParserService;

import java.io.StringReader;
import java.util.*;

/**
 * Created by Dell on 11-01-2017.
 */
public class ParserServiceImpl implements ParserService {

    private final ValueFactory valueFactory;
    private final QueryFactory queryFactory;

    public ParserServiceImpl(ValueFactory valueFactory, QueryFactory queryFactory) {
        this.valueFactory = valueFactory;
        this.queryFactory = queryFactory;
    }

    public Query parse(String sql) throws QueryNotSupportedException, QueryParsingException {
        Statement statement;
        try {
            statement = CCJSqlParserUtil.parse(new StringReader(sql));
            if (statement instanceof Insert) {
                return handleInsert(statement);
            } else if (statement instanceof Alter) {
                return handleAlter(statement);
            } else if (statement instanceof CreateTable) {
                return handleCreate(statement);
            }
            throw new QueryNotSupportedException();
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        throw new QueryParsingException();
    }

    private Query handleCreate(Statement statement) {
        CreateTable createTable = (CreateTable) statement;
        List<String> columns = new ArrayList<>();
        Set<String> indices = new HashSet<>();

        List<ColumnDefinition> columnDefinitions = createTable.getColumnDefinitions();
        if (columnDefinitions != null) {
            columnDefinitions.forEach(columnDefinition -> columns.add(columnDefinition.getColumnName()));
        }

        List<Index> indexes = createTable.getIndexes();
        if (indexes != null) {
            indexes.forEach(index -> indices.addAll(index.getColumnsNames()));
        }

        return new CreateQueryImpl(createTable.getTable().getName(), columns, indices);
    }

    private Query handleAlter(Statement statement) {
        throw new UnsupportedOperationException();
    }

    private Query handleInsert(Statement statement) {
        Insert insert = (Insert) statement;
        List<Column> columns = insert.getColumns();
        // https://github.com/JSQLParser/JSqlParser/blob/master/src/test/java/net/sf/jsqlparser/test/insert/InsertTest.java
        List<String> values = new ArrayList<>();

        ExpressionList expressionList = (ExpressionList) insert.getItemsList();
        if (expressionList != null) {
            List<Expression> expressions = expressionList.getExpressions();
            if (expressions != null) {
                for (Expression exp : expressions) {
                    Value value = valueFactory.inferValue(exp);
                    values.add(value.getValue());
                }

                if (columns != null) {
                    List<String> columnNames = new ArrayList<>();
                    columns.forEach(col -> columnNames.add(col.getColumnName()));
                    if (columnNames.size() == values.size()) {
                        return queryFactory.getQuery(insert, columnNames, values); // new InsertQueryImpl(insert.getTable().getName(), columnNames, values);
                    } else {
                        throw new IllegalStateException();
                    }
                } else {
                    return queryFactory.getQuery(insert, Collections.emptyList(), values); // new InsertQueryImpl(insert.getTable().getName(), Collections.emptyList(), values);
                }
            }
        }
        return null;
    }
}
