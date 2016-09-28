package com.fizal.xunit.demo

import com.fizal.xunit.dbunit.model.DbUnit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Requires spock-spring module for this demo.
 * Created by fmohamed on 9/23/2016.
 */
@ContextConfiguration(locations = ["classpath*:applicationContext*.xml"])
class DemoDaoSpec extends Specification {

    @Autowired
    DemoDao dao

    /**
     * Any test method annotated with @DbUnit is initialized specially by the framework.
     *
     * Order of the tables is not relevant because the framework will automatically
     * sort them out during creation time.
     *
     * To specify a custom folder to look for test data files (CSV), pass it via
     * the annotation parameter 'dataSetName'.
     */
    @DbUnit(tables = ["SCHEMA1.TABLE1", "SCHEMA2.TABLE2"])
    def "test1"() {
        expect:
        dao.query() == 5
    }
}
