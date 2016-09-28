package com.fizal.xunit.dbunit.provider;

/**
 * Created by fmohamed on 9/13/2016.
 */
public interface DDLProvider {
    String getDDLStatement(String dbObjectName);
}
