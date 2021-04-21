package project.custexceptions;

import java.io.IOException;

public class DaoException extends IOException {
    public DaoException(Throwable message) {
        super(message);
    }
}
