package project.custexceptions;

import java.sql.SQLException;

public class DBException extends SQLException {

    public DBException(Throwable cause) {
        super(cause);
    }
}
