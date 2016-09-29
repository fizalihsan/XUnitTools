package com.fizal;

/**
 * Created by fmohamed on 9/12/2016.
 */
public class Tester {

    public static void main(String[] a) throws Exception {
        System.out.println(String.join("", "a", "b", "c"));
    }

    /*private static void testH2() {
        ConnectionConfig config = new ConnectionConfig().dbName("testdb");
        System.out.println(config.getJdbcUrl());

        H2DatabaseService h2DatabaseService = new H2DatabaseService(config);
        h2DatabaseService.createSchemas("myschema");
        h2DatabaseService.execute("CREATE TABLE myschema.TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))");

        JdbcTemplate jdbcTemplate = h2DatabaseService.getJdbcTemplate();
        Integer count = jdbcTemplate.queryForObject("select count(1) from myschema.TEST", Integer.class);
        System.out.println(count);

        h2DatabaseService.dropAllDbOjects();
    }*/
}
