package com.fizal.xunit.dbunit.model;

import com.fizal.xunit.dbunit.rule.SpockDbUnitExtension;
import org.spockframework.runtime.extension.ExtensionAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fmohamed on 9/13/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@ExtensionAnnotation(SpockDbUnitExtension.class)
public @interface DbUnit {

    String dataSetName() default "";

    String[] tables();
}
