package com.fizal.xunit.dbunit.extension

import com.fizal.xunit.dbunit.model.TestMethodConfig
import com.fizal.xunit.dbunit.service.DBUnitBootStrapService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by fmohamed on 9/21/2016.
 */
class DbUnitRunListener extends AbstractRunListener {
    private static Logger LOGGER = LoggerFactory.getLogger(DbUnitRunListener.class);

    @Autowired
    private DBUnitBootStrapService bootStrapService;

    private TestMethodConfig config

    DbUnitRunListener(TestMethodConfig config) {
        this.config = config
    }

    void beforeSpec(SpecInfo spec) {
        if (bootStrapService == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext(["applicationContext-h2-dbunit.xml"] as String[]);
            bootStrapService = context.getBean(DBUnitBootStrapService.class);
        }
    }

    void beforeFeature(FeatureInfo feature) {
        LOGGER.info("Initializing test data for {}.{}", config.testClassName, config.testMethodName)
        bootStrapService.bootstrap(config)
    }

    void afterFeature(FeatureInfo feature) {
    }

    void afterSpec(SpecInfo spec) {
    }
}