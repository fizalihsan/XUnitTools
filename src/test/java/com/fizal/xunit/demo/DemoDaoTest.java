package com.fizal.xunit.demo;

import com.fizal.xunit.dbunit.AbstractUnitTest;
import com.fizal.xunit.dbunit.model.DbUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by fmohamed on 9/13/2016.
 */
@ActiveProfiles("test")
@ContextConfiguration(locations = {"classpath*:applicationContext*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

/**
 * Every DBUnit test class should extend 'AbstractUnitTest'
 */
public class DemoDaoTest extends AbstractUnitTest {

    @Autowired
    private DemoDao dao;

    /**
     * Any test method annotated with @DbUnit is initialized specially by the framework.
     * <p/>
     * Order of the tables is not relevant because the framework will automatically
     * sort them out during creation time.
     * <p/>
     * To specify a custom folder to look for test data files (CSV), pass it via
     * the annotation parameter 'dataSetName'.
     */
    @Test
    @DbUnit(tables = {"SCHEMA1.TABLE1", "SCHEMA2.TABLE2"})
    public void test1() {
        assertEquals(5, dao.query());
    }

}
