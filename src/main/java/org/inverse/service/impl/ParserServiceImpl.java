package org.inverse.service.impl;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitorAdapter;
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
import org.inverse.query.impl.CreateQueryImpl;
import org.inverse.service.ParserService;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dell on 11-01-2017.
 */
public class ParserServiceImpl implements ParserService {

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
        ItemsList itemsList = insert.getItemsList();
        itemsList.accept(new ItemsListVisitorAdapter(){
            @Override
            public void visit(ExpressionList expressionList) {
                super.visit(expressionList);
            }
        });
        return null;
    }
}
