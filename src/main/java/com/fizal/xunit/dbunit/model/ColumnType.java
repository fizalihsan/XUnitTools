package com.fizal.xunit.dbunit.model;

import org.springframework.format.Printer;

/**
 * Created by fmohamed on 9/20/2016.
 */
public enum ColumnType {
    BIT(-7),
    TINYINT(-6),
    BIGINT(-5),
    LONGVARBINARY(-4),
    VARBINARY(-3),
    BINARY(-2),
    LONGVARCHAR(-1, new StringPrinter()),
    NULL(0),
    CHAR(1, new StringPrinter()),
    NUMERIC(2),
    DECIMAL(3),
    INTEGER(4),
    SMALLINT(5),
    FLOAT(6),
    REAL(7),
    DOUBLE(8),
    VARCHAR(12, new StringPrinter()),
    DATE(91, new StringPrinter()),
    TIME(92, new StringPrinter()),
    TIMESTAMP(93, new StringPrinter()),
    OTHER(1111);

    private int jdbcType;
    private Printer<String> printer;

    ColumnType(int jdbcType) {
        this.jdbcType = jdbcType;
    }

    ColumnType(int jdbcType, Printer<String> printer) {
        this.jdbcType = jdbcType;
        this.printer = printer;
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public Printer<String> getPrinter() {
        return printer;
    }

    public static String print(int jdbcType, String value) {

        for (ColumnType type : values()) {
            if (type.getJdbcType() == jdbcType) {
                Printer<String> printer = type.getPrinter();
                return printer == null ? value : printer.print(value, null);
            }
        }
        return value;
    }
}
