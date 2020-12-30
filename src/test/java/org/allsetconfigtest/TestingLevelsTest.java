package org.allsetconfigtest;

import static org.allsetconfigtest.TestingLevels.UNIT;
import static org.allsetconfigtest.TestingObject.constructorUtilClass;

import java.lang.reflect.InvocationTargetException;

import org.testng.annotations.Test;

@Test
public class TestingLevelsTest {

    @Test(priority = 0, groups = UNIT, expectedExceptions = InvocationTargetException.class)
    public void StandardTestingConstructorTest() throws Exception {
        constructorUtilClass(TestingLevels.class);
    }
}
