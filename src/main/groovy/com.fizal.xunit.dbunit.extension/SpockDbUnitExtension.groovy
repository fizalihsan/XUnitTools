package com.fizal.xunit.dbunit.extension

import com.fizal.xunit.dbunit.model.DbUnit
import com.fizal.xunit.dbunit.model.TestMethodConfig
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo

/**
 * Created by fmohamed on 9/21/2016.
 */

class SpockDbUnitExtension extends AbstractAnnotationDrivenExtension<DbUnit> {
    def feature, annotation

    @Override
    void visitSpecAnnotation(DbUnit annotation, SpecInfo spec) {
    }

    @Override
    void visitFeatureAnnotation(DbUnit annotation, FeatureInfo feature) {
        this.feature = feature.name
        this.annotation = annotation
    }

    void visitSpec(SpecInfo spec) {
        def testMethodConfig = new TestMethodConfig(annotation)
        testMethodConfig.testClassName = spec.package + "." + spec.name
        testMethodConfig.testMethodName = feature

        spec.addListener(new DbUnitRunListener(testMethodConfig))
    }
}