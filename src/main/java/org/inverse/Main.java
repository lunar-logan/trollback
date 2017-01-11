package org.inverse;

import org.inverse.exception.QueryNotSupportedException;
import org.inverse.exception.QueryParsingException;
import org.inverse.service.RollbackService;

/**
 * Created by Dell on 11-01-2017.
 */
public class Main {
    public static void main(String[] args) throws QueryParsingException, QueryNotSupportedException {
        String sql = "insert into t (a,b,c) values('ds','ds',5);";

        RollbackService rollbackService = new RollbackService() {
            public String getRollbackQuery(String query) {
                return null;
            }
        };
        String rollbackQuery = rollbackService.getRollbackQuery(sql);
        System.out.println(rollbackQuery);
    }
}
