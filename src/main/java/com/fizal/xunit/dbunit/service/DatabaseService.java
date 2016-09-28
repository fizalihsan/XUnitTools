package com.fizal.xunit.dbunit.service;

/**
 * Created by fmohamed on 9/21/2016.
 */
public interface DatabaseService {

    void execute(String sql);

    void createSchemas(String... schemas);

    void dropAllDbOjects();
}
