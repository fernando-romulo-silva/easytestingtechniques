package org.allsetconfigtest;

import static org.allsetconfigtest.TestingLevels.UNIT;
import static org.allsetconfigtest.TestingObject.constructorUtilClass;
import static org.allsetconfigtest.TestingObject.enumItems;
import static org.allsetconfigtest.TestingObject.equalsAndHashCode;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.attribute.AclEntryFlag;

import org.allsetconfigtest.combinatorial.ObjectCombinatorialParamSpec;
import org.testng.annotations.Test;

@Test
public class TestingObjectTest {

    @Test(priority = 0, groups = UNIT, expectedExceptions = IllegalArgumentException.class)
    public void TestingObjectConstructorExceptionTest() throws Exception {
	constructorUtilClass(null);
    }

    @Test(priority = 1, groups = UNIT, expectedExceptions = InvocationTargetException.class)
    public void TestingObjectConstructorTest() throws Exception {
	constructorUtilClass(TestingObject.class);
    }

    @Test(priority = 3, groups = UNIT, expectedExceptions = IllegalStateException.class)
    public void TestingObjectNotPrivateConstructorTest() throws Exception {
	constructorUtilClass(Object.class);
    }

    @Test(priority = 4, groups = UNIT, expectedExceptions = IllegalArgumentException.class)
    public void equalsAndHashCodeExceptionTest() {
	equalsAndHashCode(null);
    }

    @Test(priority = 5, groups = UNIT)
    public void equalsAndHashCodeTest() {
	equalsAndHashCode(ObjectCombinatorialParamSpec.class);
    }

    @Test(priority = 6, groups = UNIT, expectedExceptions = IllegalArgumentException.class)
    public void enumItemsExceptionParameatersTest() {
	enumItems(null);
    }

    @Test(priority = 7, groups = UNIT)
    public void enumItemsTest() {
	enumItems(AclEntryFlag.class);
    }
    

    @Test(priority = 8, groups = UNIT)
    public void equalsAndHashCodeWithParametersTest() {

	final String name1 = "Mary";
	final String name2 = "Mary";
	final String name3 = "Ana";

	equalsAndHashCode(name1, name2, name3);
    }

    @Test(priority = 9, groups = UNIT, expectedExceptions = { IllegalArgumentException.class, IllegalStateException.class })
    public void equalsAndHashCodeWithParametersExceptionTest() {

	final String name1 = "Mary";
	final String name2 = "Mary";
	final String name3 = "Mary";

	equalsAndHashCode(name1, name2, name3);
    }
}
