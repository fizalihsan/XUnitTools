package com.fizal.xunit.dbunit.util;

import com.fizal.xunit.dbunit.model.Table;
import com.fizal.xunit.dbunit.provider.DDLProvider;
import com.fizal.xunit.dbunit.provider.DefaultDDLProvider;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import java.util.*;

/**
 * Created by fmohamed on 9/20/2016.
 */
public final class TableDeployOrderSorter {

    private static final TableNameExtractor TABLE_NAME_EXTRACTOR = new TableNameExtractor();

    private static final DDLProvider DDL_PROVIDER = new DefaultDDLProvider();

    public static Collection<String> sort(List<String> tableNames) {
//        String path = "C:\\Fizal\\SourceCode\\Branches\\Login\\branches\\B-24790-IntegrationTestFix\\src\\main\\database\\tables\\";

        Map<String, Table> tables = new HashMap<>();

        for (String tableName : tableNames) {
//            String schema = tableName.split("\\.")[0];
//            String filePath = path + schema + "\\" + tableName + ".sql";
//            List<String> lines = FileUtils.readLines(new File(filePath), defaultCharset());
            String ddlStatement = DDL_PROVIDER.getDDLStatement(tableName);

            Table table = new Table(tableName);

            for (String line : ddlStatement.split("\n")) {
                if (line.contains("REFERENCES")) {
                    line = line.replace("REFERENCES", "");
                    line = line.replace("\"", "");
                    table.dependsOn(line.trim());
                }
            }

            tables.put(tableName, table);
        }

        for (String tableName : tables.keySet()) {
            Table table = tables.get(tableName);

            int newOrder = calculateOrder(tables, tableName);
            if (newOrder > table.getOrder()) {
                table.setOrder(newOrder);
            }
        }

        List<Table> sortedTables = new ArrayList<>(tables.values());
        Collections.sort(sortedTables);

        return Collections2.transform(sortedTables, TABLE_NAME_EXTRACTOR);
    }

    protected static class TableNameExtractor implements Function<Table, String> {
        @Override
        public String apply(Table table) {
            return table.getName();
        }
    }

    protected static int calculateOrder(Map<String, Table> tables, String tableName) {
        Table table = tables.get(tableName);
        if (table == null) {
            throw new RuntimeException("Table not defined in the annotation list : " + tableName);
        }
        int newOrder = table.getOrder();

        for (String name : table.getDependsOn()) {
            int parentOrder = calculateOrder(tables, name);
            newOrder = (parentOrder + 1) > newOrder ? parentOrder + 1 : newOrder;
        }

        return newOrder;
    }

}
