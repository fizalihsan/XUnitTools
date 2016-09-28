package com.fizal.xunit.dbunit.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.fizal.xunit.dbunit.model.ColumnType.print;

/**
 * Created by fmohamed on 9/20/2016.
 */
public final class TestDataGenerator {

    private static final String DB_URL = "jdbc:db2://10.1.1.8:446/CDNXDB2T";
    private static final String DB_USER = "cxapt075";
    private static final String DB_PASSWORD = "comdata";

    static {
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading DB2 driver class", e);
        }
    }

    public static void main(String[] args) throws Exception {
        String sql = "select * from DBO.SCHOOLS";

        System.out.println(new TestDataGenerator().execute(sql, 5));
    }

    public String execute(String sql, int maxRows) throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();

            Statement statement = conn.createStatement();
            statement.setMaxRows(maxRows);
            return toCsv(statement.executeQuery(sql));
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    protected String toCsv(ResultSet rs) throws SQLException {
        boolean headerIncluded = false;
        StringBuilder builder = new StringBuilder();

        while (rs.next()) {
            if (!headerIncluded) {
                builder.append(getColumnNames(rs)).append("\n");
                headerIncluded = true;
            }
            builder.append(getColumnValues(rs)).append("\n");
        }

        return builder.toString();
    }

    protected String getColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        List<String> list = new ArrayList<>(metaData.getColumnCount());
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            list.add(metaData.getColumnName(i));
        }
        return join(list);
    }

    protected String getColumnValues(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        List<String> list = new ArrayList<>(metaData.getColumnCount());
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            list.add(print(metaData.getColumnType(i), rs.getString(i)));
        }
        return join(list);
    }

    protected String join(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s).append(", ");
        }
        builder.deleteCharAt(builder.length() - 2); //delete the last extra comma
        return builder.toString();
    }

    protected Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        System.err.println("The connection is successfully obtained");
        return connection;
    }

}
