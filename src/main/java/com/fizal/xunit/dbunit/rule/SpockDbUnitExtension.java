package com.fizal.xunit.dbunit.rule;

import com.fizal.xunit.dbunit.model.DbUnit;
import com.fizal.xunit.dbunit.model.TestMethodConfig;
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.SpecInfo;

/**
 * Created by fmohamed on 9/23/2016.
 */
public class SpockDbUnitExtension extends AbstractAnnotationDrivenExtension<DbUnit> {
    private String feature;
    private DbUnit annotation;

    @Override
    public void visitSpecAnnotation(DbUnit annotation, SpecInfo spec) {
    }

    @Override
    public void visitFeatureAnnotation(DbUnit annotation, FeatureInfo feature) {
        this.feature = feature.getName();
        this.annotation = annotation;
    }

    public void visitSpec(SpecInfo spec) {
        TestMethodConfig testMethodConfig = new TestMethodConfig(this.annotation);
        testMethodConfig.setTestClassName(spec.getPackage() + "." + spec.getName());
        testMethodConfig.setTestMethodName(this.feature);

        spec.addListener(new SpockDbUnitListener(testMethodConfig));
    }
}
