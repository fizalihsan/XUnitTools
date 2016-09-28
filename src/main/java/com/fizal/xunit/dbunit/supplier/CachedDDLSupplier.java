package com.fizal.xunit.dbunit.supplier;

import com.fizal.xunit.dbunit.util.LRUCache;

/**
 * Comment here about the class
 * User: Fizal
 * Date: 9/27/2016
 * Time: 10:47 PM
 */
public class CachedDDLSupplier implements DDLSupplier {

    private final DDLSupplier delegate;

    private static final LRUCache<String, String> DDL_CACHE = new LRUCache<>(100);

    public CachedDDLSupplier(DDLSupplier delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getDDL(String dbObjectName) {
        String ddl = DDL_CACHE.get(dbObjectName);
        if (ddl == null) {
            ddl = this.delegate.getDDL(dbObjectName);
            DDL_CACHE.put(dbObjectName, ddl);
        }
        return ddl;
    }

}
