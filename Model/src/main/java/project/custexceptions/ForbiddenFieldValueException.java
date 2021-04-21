package project.custexceptions;

import java.io.IOException;

public class ForbiddenFieldValueException extends IOException {

    public ForbiddenFieldValueException(String message) {
        super(message);
    }
}
