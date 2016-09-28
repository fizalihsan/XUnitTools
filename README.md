# XUnitTools

## Overview

This is a DB Unit Test Framework that makes writing DB unit tests fun.

As shown in examples below, each DB unit test method is annotated with `@DbUnit`. This annotation takes in a list of table names (*with schema prefix*).

Behind the scenes, for each listed table, DDL files are searched in classpath. For example, if the table name is ***SCHEMA1.TABLE1***, then the framework expects a file ***/SCHEMA1/SCHEMA1.TABLE1.sql*** in classpath. The test method fails if the DDL SQL file is not found.

Similarly, for each listed table, test data file is searched in classpath. For example, for the talbe ***SCHEMA1.TABLE1***, it looks for a CSV file under ***/src/test/resources/testdata/com/fizal/dao/DemoDaoTest/test1/SCHEMA1.TABLE1.csv***.

**JUnit Example**

```java
package com.fizal.dao;

public class DemoDaoTest extends AbstractUnitTest {

    @Autowired
    private DemoDao dao;
    
    @Test
    @DbUnit(tables = {"SCHEMA1.TABLE1", "SCHEMA2.TABLE2"})
    public void test1() {
        assertEquals(5, dao.query());
    }
}
```

**Spock Example**

```groovy
@ContextConfiguration(locations = ["classpath*:applicationContext*.xml"])
class DemoDaoSpec extends Specification {
    @Autowired
    DemoDao dao

    @DbUnit(tables = ["SCHEMA1.TABLE1", "SCHEMA2.TABLE2"])
    def "test1"() {
        expect:
        dao.query() == 5
    }
}
```

## Features
