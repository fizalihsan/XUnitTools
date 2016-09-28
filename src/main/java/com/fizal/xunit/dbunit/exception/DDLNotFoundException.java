package com.fizal.xunit.dbunit.exception;

/**
 * Created by fmohamed on 9/13/2016.
 */
public class DDLNotFoundException extends RuntimeException {
    public DDLNotFoundException() {
        super();
    }

    public DDLNotFoundException(String message) {
        super(message);
    }

    public DDLNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
