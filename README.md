# XUnitTools

## Overview

This is a DB Unit Test Framework that makes writing DB unit tests fun.

As shown in examples below, each DB unit test method is annotated with `@DbUnit`. This annotation takes in a list of table names (*with schema prefix*).

**JUnit Example**

```java
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
