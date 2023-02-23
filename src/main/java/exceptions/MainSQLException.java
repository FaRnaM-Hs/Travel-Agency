package exceptions;

import java.sql.SQLException;

public class MainSQLException extends RuntimeException {
    public MainSQLException(SQLException e) {
        super(e);
    }
}