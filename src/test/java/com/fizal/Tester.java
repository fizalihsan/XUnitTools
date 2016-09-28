package com.fizal;

import com.fizal.xunit.dbunit.util.StringUtil;

/**
 * Created by fmohamed on 9/12/2016.
 */
public class Tester {

    public static void main(String[] a) throws Exception {
        String ddl = "CREATE INDEX \"DBO\".\"XSCRLS07\"\n" +
                "\tON \"DBO\".\"CRD_LSTG\"\n" +
                "\t(\"CRD_NBR\", \n" +
                "\t  \"CRD_EMP_ID\", \n" +
                "\t  SUBSTR(CRD_NBR,12,5))\n" +
                "\tNOT CLUSTER\n" +
                "\tPADDED\n" +
                "\tUSING STOGROUP SGCD0001\n" +
                "\t  PRIQTY -1\n" +
                "\t  SECQTY -1\n" +
                "\tPCTFREE 5\n" +
                "\tBUFFERPOOL BP7     \n" +
                "\tPIECESIZE 2097152 K;";
        String s = StringUtil.extractObjectDef(ddl);

        System.out.println(s);
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
