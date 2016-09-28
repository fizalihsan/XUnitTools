package com.fizal.xunit.dbunit.rule;

import com.fizal.xunit.dbunit.model.DbUnit;
import com.fizal.xunit.dbunit.model.TestMethodConfig;
import com.fizal.xunit.dbunit.service.DBUnitBootStrapService;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by fmohamed on 9/13/2016.
 */
public class DbUnitTestRule implements MethodRule {
    private static Logger LOGGER = LoggerFactory.getLogger(DbUnitTestRule.class);

    @Autowired
    private DBUnitBootStrapService bootStrapService;

    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        final DbUnit annotation = method.getAnnotation(DbUnit.class);

        if (bootStrapService == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext-h2-dbunit.xml"});
            bootStrapService = context.getBean(DBUnitBootStrapService.class);
        }


        if (annotation != null) {
            String className = method.getMethod().getDeclaringClass().getCanonicalName();
            String methodName = method.getName();

            TestMethodConfig testMethodConfig = new TestMethodConfig(annotation);
            testMethodConfig.setTestClassName(className);
            testMethodConfig.setTestMethodName(methodName);

            LOGGER.info("Initializing test data for {}.{}", className, methodName);
            bootStrapService.bootstrap(testMethodConfig);
        }

        return base;
    }
}
