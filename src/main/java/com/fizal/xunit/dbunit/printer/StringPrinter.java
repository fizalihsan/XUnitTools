package com.fizal.xunit.dbunit.printer;

import org.springframework.format.Printer;

import java.util.Locale;

/**
 * Created by fmohamed on 9/20/2016.
 */
public class StringPrinter implements Printer<String> {
    @Override
    public String print(String value, Locale locale) {
        return value == null ? "''" : "'" + value.trim() + "'";
    }
}
