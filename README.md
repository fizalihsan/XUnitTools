# XUnitTools

## Synopsis

This is a simple framework that makes writing DB unit tests easy and fun.

As shown in examples below, each DB unit test method is annotated with `@DbUnit`. It takes a list of table names (*with schema prefix*) as input.

This framework uses *convention-over-configuration* approach to reduce unnecessary configuration pain to the developers. 

### Table Creation

Each listed table is automatically created in an in-memory database behind the scenes and loaded with test data, if provided. In order for the framework to create and load the tables, DDL files and data files should be provided as SQL and CSV files in classpath.

For example, if the table name is ***SCHEMA1.TABLE1***, then a file ***/SCHEMA1/SCHEMA1.TABLE1.sql*** is expected in classpath. If not found, the test fails throwing `DDLNotFoundException`. 

### Loading test data

Similarly, for table ***SCHEMA1.TABLE1***, it looks for a CSV file under ***/src/test/resources/testdata/com/fizal/dao/DemoDaoTest/test1/SCHEMA1.TABLE1.csv***, where ***/src/test/resources/testdata*** is the root followed by the same directory as your test class including the test class name and test method name.

If the data file is not found there, then it searches for any common data files under ***/src/test/resources/testdata***. Files under this directory is shared by all test cases, if needed.

## Code Examples

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

## Motivation

Ask any developer and they will tell you this: writing DB unit test is not easy. Unit tests are not supposed to connect to external resources like DB, but there are cases to test out the data access queries against a real database instead of mocks. Typically, most applications connect to a physical database. The problem with this approach is that

* Tests fail, if the remote DB is unavailable
* Tests fail sporadically since the data is shared by all tests
* Time to set up new test data is also high since it is shared 
* Test are slow due to network I/O

## Features

* Uses H2 in-memory DB
* Convention-over-configuration
* Order of the tables
* Fresh fixture model
* Sharing test data
