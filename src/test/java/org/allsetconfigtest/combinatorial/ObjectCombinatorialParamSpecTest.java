package org.allsetconfigtest.combinatorial;

import static java.util.Arrays.asList;
import static org.allsetconfigtest.TestingLevels.UNIT;
import static org.allsetconfigtest.TestingObject.equalsAndHashCode;
import static org.allsetconfigtest.combinatorial.ObjectCombinatorialParamSpec.ToType.ofObject;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.allsetconfigtest.ValuesSupplier;
import org.allsetconfigtest.combinatorial.ObjectCombinatorialParamSpec.ToType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class ObjectCombinatorialParamSpecTest {

    @Test(priority = 0, groups = UNIT)
    public void equalsAndHashCodeTest() {
	equalsAndHashCode(ObjectCombinatorialParamSpec.class);
    }

    // @formatter:off
    @DataProvider
    public Object[][] ofObjectOneValidAndVariousInvalidExceptionData() { //
        return new Object[][] { //
            {  null    ,    new String[] { "invalid1", "invalid2", null }  }, //
            {  "test"  ,    new String[] { }                               }, //
            {  "test"  ,    null                                           }, // 
        };
    }
    // @formatter:on   

    @Test(priority = 1, groups = UNIT, dataProvider = "ofObjectOneValidAndVariousInvalidExceptionData")
    public void ofObjectOneValidAndVariousInvalidExceptionTest(final String validArgument, final String[] invalidArguments) {

	assertThatThrownBy(() -> ToType.<String>ofObject().addValidValue(validArgument).addInvalidValues(invalidArguments)) //
		.isInstanceOf(IllegalArgumentException.class);
    }

    // @formatter:off
    @DataProvider
    public Object[][] ofObjectOneValidAndVariousInvalidData() {
        return new Object[][] { 
            {  1       ,    new Integer[] { -1, 0 }       }, //
            {  "test"  ,    new String[] { null, EMPTY }  }, //
        };
    }
    // @formatter:on  

    @Test(priority = 2, groups = UNIT, dataProvider = "ofObjectOneValidAndVariousInvalidData")
    public void ofObjectOneValidAndVariousInvalidTest(final Object validArgument, final Object[] invalidArguments) {
	final var parameter = ofObject().addValidValue(validArgument).addInvalidValues(invalidArguments);

	assertThat(parameter.getValidValues()).contains(validArgument);

	assertThat(parameter.getInvalidValues()).isEqualTo(new LinkedHashSet<>(asList(invalidArguments)));
    }

    @Test(priority = 3, groups = UNIT)
    public void ofObjectOneValidExceptionTest() {

	assertThatThrownBy(() -> ofObject().addValidValue(null)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage("Parameter 'value' is invalid!");
    }

    @Test(priority = 4, groups = UNIT)
    public void ofObjectOneValidTest() {
	final var value = "MyParameter";

	final var parameter = ofObject().addValidValue(value);

	assertThat(parameter.getValidValues()).contains(value);
    }

    // @formatter:off
    @DataProvider
    public Object[][] ofObjectOneValidAndOneInvalidExceptionData() {
        return new Object[][] { 
            {    null         , "valid"   }
        };
    }
    // @formatter:on  

    @Test(priority = 5, groups = UNIT, dataProvider = "ofObjectOneValidAndOneInvalidExceptionData")
    public void ofObjectOneValidAndOneInvalidExceptionTest(final Object validArgument, final Object invalidArgument) {

	assertThatThrownBy(() -> ofObject().addValidValue(validArgument).addInvalidValue(invalidArgument)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage("Parameter 'value' is invalid!");

    }

    // @formatter:off
    @DataProvider
    public Object[][] ofObjectOneValidAndOneInvalidData() {
        return new Object[][] { 
            {    "valid"   , "invalid"   }, //
            {    100       , 1000        }, //
        };
    }
    // @formatter:on

    @Test(priority = 6, groups = UNIT, dataProvider = "ofObjectOneValidAndOneInvalidData")
    public void ofObjectOneValidAndOneInvalidTest(final Object validArgument, final Object invalidArgument) {
	final var parameter = ofObject().addValidValue(validArgument).addInvalidValue(invalidArgument);

	assertThat(parameter.getValidValues()).contains(validArgument);

	assertThat(parameter.getInvalidValues()).contains(invalidArgument);
    }

    final ValuesSupplier<Object> validArgumentSupplier = new ValuesSupplier<Object>() {

	@Override
	public List<Object> get() {

	    final Object val1 = "validValue1";
	    final Object val2 = "validValue2";

	    return List.of(val1, val2);
	}
    };

    final ValuesSupplier<Object> invalidArgumentSupplier = new ValuesSupplier<Object>() {

	@Override
	public List<Object> get() {

	    final Object val1 = "invalidValue1";
	    final Object val2 = "invalidValue2";

	    return List.of(val1, val2);
	}
    };

    // @formatter:off
    @DataProvider
    public Object[][] ofObjectValidSupplierAndInvalidSupplierExceptionData() {
        return new Object[][] { 
            {    null                  , invalidArgumentSupplier   }, //
            {    validArgumentSupplier , null                      }, //
        };
    }
    // @formatter:on  

    @Test(priority = 7, groups = UNIT, dataProvider = "ofObjectValidSupplierAndInvalidSupplierExceptionData")
    public void ofObjectValidSupplierAndInvalidSupplierExceptionTest(final ValuesSupplier<Object> validArgument, final ValuesSupplier<Object> invalidArgument) {

	assertThatThrownBy(() -> ofObject().addValidValues(validArgument).addInvalidValues(invalidArgument)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage("Parameter 'valuesSupplier' is invalid!");
    }

    @Test(priority = 8, groups = UNIT)
    public void ofObjectValidSupplierAndInvalidSupplierTest() {
	final var parameter = ofObject().addValidValues(validArgumentSupplier).addInvalidValues(invalidArgumentSupplier);

	assertThat(parameter.getValidValues()).containsAll(Set.of("validValue1", "validValue2"));

	assertThat(parameter.getInvalidValues()).containsAll(Set.of("invalidValue1", "invalidValue2"));
    }
}