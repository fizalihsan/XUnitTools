package com.fizal.xunit.dbunit;

import com.fizal.xunit.dbunit.rule.DbUnitTestRule;
import org.junit.Rule;
import org.junit.rules.MethodRule;

/**
 * Created by fmohamed on 9/13/2016.
 */
public abstract class AbstractUnitTest {

    @Rule
    public MethodRule dbUnitTestMethodRule = new DbUnitTestRule();


}
