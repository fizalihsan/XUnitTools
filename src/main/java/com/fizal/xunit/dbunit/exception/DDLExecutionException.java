package com.fizal.xunit.dbunit.exception;

/**
 * Created by fmohamed on 9/13/2016.
 */
public class DDLExecutionException extends RuntimeException {
    public DDLExecutionException() {
        super();
    }

    public DDLExecutionException(String message) {
        super(message);
    }

    public DDLExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
