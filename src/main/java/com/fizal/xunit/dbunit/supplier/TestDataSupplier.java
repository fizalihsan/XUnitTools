package com.fizal.xunit.dbunit.supplier;

import com.fizal.xunit.dbunit.model.TestMethodConfig;

import java.util.List;

/**
 * Created by fmohamed on 9/13/2016.
 */
public interface TestDataSupplier {
    List<String> insertStatement(TestMethodConfig testMethodConfig, String table);
}
