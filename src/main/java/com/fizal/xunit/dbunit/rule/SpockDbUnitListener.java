package com.fizal.xunit.dbunit.rule;

import com.fizal.xunit.dbunit.model.TestMethodConfig;
import com.fizal.xunit.dbunit.service.DBUnitBootStrapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spockframework.runtime.AbstractRunListener;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.SpecInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by fmohamed on 9/23/2016.
 */
public class SpockDbUnitListener extends AbstractRunListener {
    private static Logger LOGGER = LoggerFactory.getLogger(SpockDbUnitListener.class);

    @Autowired
    private DBUnitBootStrapService bootStrapService;

    private TestMethodConfig config;

    SpockDbUnitListener(TestMethodConfig config) {
        this.config = config;
    }

    public void beforeSpec(SpecInfo spec) {
        if (bootStrapService == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext-h2-dbunit.xml"});
            bootStrapService = context.getBean(DBUnitBootStrapService.class);
        }
    }

    public void beforeFeature(FeatureInfo feature) {
        LOGGER.info("Initializing test data for {}.{}", config.getTestClassName(), config.getTestMethodName());
        bootStrapService.bootstrap(config);
    }

    public void afterFeature(FeatureInfo feature) {
    }

    public void afterSpec(SpecInfo spec) {
    }
}
