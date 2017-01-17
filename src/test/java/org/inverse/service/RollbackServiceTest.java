package org.inverse.service;

import org.inverse.exception.QueryNotSupportedException;
import org.inverse.exception.QueryParsingException;
import org.inverse.query.impl.ValueFactoryImpl;
import org.inverse.service.impl.ParserServiceImpl;
import org.inverse.service.impl.QueryInverterServiceImpl;
import org.inverse.service.impl.RollbackServiceImpl;
import org.junit.Test;

/**
 * Created by anurag on 12/1/17.
 */
public class RollbackServiceTest {
    private SchemaService getDummySchemaService() {
        return (tableName) -> "CREATE TABLE tbl (\n" +
                "  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,\n" +
                "  a INT NOT NULL ,\n" +
                "  b INT NOT NULL ,\n" +
                "  c INT NOT NULL ,\n" +
                "  key a_key (a),\n" +
                "key b_key (b)" +
                ");";
    }

    @Test
    public void testGetRollbackQuery() {
        ParserService parserService = new ParserServiceImpl(new ValueFactoryImpl(), queryFactory);
        SchemaService dummySchemaService = getDummySchemaService();
        System.out.println(dummySchemaService.getSchema("23456"));
        RollbackService rollbackService = new RollbackServiceImpl(
                dummySchemaService,
                parserService,
                new QueryInverterServiceImpl(parserService)
        );

        String sql = "insert into tbl (id, a,b,c) values ('11', '1','2','3')";
        try {
            String rollbackQuery = rollbackService.getRollbackQuery(sql);
            System.out.println(rollbackQuery);
        } catch (QueryParsingException | QueryNotSupportedException e) {
            e.printStackTrace();
        }
    }
}