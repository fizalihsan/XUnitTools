package com.fizal.xunit.dbunit.provider;

import com.fizal.xunit.dbunit.model.TestMethodConfig;

import java.util.List;

/**
 * Created by fmohamed on 9/13/2016.
 */
public interface TestDataProvider {
    List<String> insertStatement(TestMethodConfig testMethodConfig, String table);
}
