package com.fizal.xunit.dbunit.exception;

/**
 * Created by fmohamed on 9/13/2016.
 */
public class InvalidTestDataFileException extends RuntimeException {
    public InvalidTestDataFileException() {
        super();
    }

    public InvalidTestDataFileException(String message) {
        super(message);
    }

    public InvalidTestDataFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
