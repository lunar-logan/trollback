package org.inverse.service.impl;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.insert.Insert;
import org.inverse.exception.QueryNotSupportedException;
import org.inverse.exception.QueryParsingException;
import org.inverse.query.Query;
import org.inverse.service.ParserService;

import java.io.StringReader;

/**
 * Created by Dell on 11-01-2017.
 */
public class ParserServiceImpl implements ParserService {

    public Query parse(String sql) throws QueryNotSupportedException, QueryParsingException {
        Statement statement;
        try {
            statement = CCJSqlParserUtil.parse(new StringReader(sql));
            if (statement instanceof Insert) {
                return handleInsert();
            } else if (statement instanceof Alter) {
                return handleAlter();
            } else if(statement instanceof CreateTable) {
                return handleCreate(statement);
            }
            throw new QueryNotSupportedException();
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        throw new QueryParsingException();
    }

    private Query handleCreate(Statement statement) {

        return null;
    }

    private Query handleAlter() {
        return null;
    }

    private Query handleInsert() {
        return null;
    }
}
