package com.fizal.xunit.dbunit.cleanser;

/**
 * DDL files under /src/main/database are extracted straight out of DB2 database in production.
 * They contain vendor-specific keywords that are not compatible with H2 in-memory database.
 * <p/>
 * Before executing these DDL in H2, those vendor-specific keywords and lines need to be cleansed
 * or removed. The implementation of this interface is meant for that cleansing operation.
 * <p/>
 * Created by fmohamed on 9/16/2016.
 */
public interface DDLCleanser {

    String cleanse(String ddl);
}
