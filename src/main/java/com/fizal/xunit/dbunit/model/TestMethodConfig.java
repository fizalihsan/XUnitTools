package com.fizal.xunit.dbunit.model;

import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by fmohamed on 9/13/2016.
 */
public final class TestMethodConfig {
    private DbUnit dbUnit;
    private String testClassName;
    private String testMethodName;

    public TestMethodConfig(DbUnit dbUnit) {
        this.dbUnit = dbUnit;
    }

    public String getDataSetName() {
        return dbUnit.dataSetName();
    }

    public List<String> getTableNames() {
        return Arrays.asList(dbUnit.tables());
    }

    public Set<String> getSchemaNames() {
        Set<String> schemas = new LinkedHashSet<String>();

        for (String table : getTableNames()) {
            String schema = getSchema(table);
            if (!StringUtils.isEmpty(schema)) {
                schemas.add(schema);
            }
        }

        return schemas;
    }

    /**
     * group table names by schema name prefix
     *
     * @return
     */
    public Map<String, List<String>> groupTablesBySchema() {
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();

        for (String table : dbUnit.tables()) {
            String schema = getSchema(table);

            if (!map.containsKey(schema)) {
                map.put(schema, new LinkedList<String>());
            }

            List<String> tables = map.get(schema);
            tables.add(getTableName(table));
        }
        return map;
    }

    protected String getSchema(String table) {
        if (table.contains(".")) {
            return table.substring(0, table.indexOf(".")).toUpperCase();
        }
        return "";
    }

    protected String getTableName(String table) {
        if (table.contains(".")) {
            return table.substring(table.indexOf(".") + 1).toUpperCase();
        }
        return table.toUpperCase();
    }

    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }
}
