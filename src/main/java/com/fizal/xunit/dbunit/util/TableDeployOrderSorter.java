package com.fizal.xunit.dbunit.util;

import com.fizal.xunit.dbunit.model.Table;
import com.fizal.xunit.dbunit.supplier.DDLSupplier;
import com.fizal.xunit.dbunit.supplier.DefaultDDLSupplier;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by fmohamed on 9/20/2016.
 */
public final class TableDeployOrderSorter {
    private static final DDLSupplier DDL_PROVIDER = new DefaultDDLSupplier();

    public static Collection<String> sort(List<String> tableNames) {
        Map<String, Table> tables = new HashMap<>();

        for (String tableName : tableNames) {
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
//        Collections.sort(sortedTables);

        return sortedTables
                .stream()
                .sorted()
                .map(Table::getName)
                .collect(toList());
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
