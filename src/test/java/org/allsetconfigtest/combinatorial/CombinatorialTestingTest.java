package org.allsetconfigtest.combinatorial;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.allsetconfigtest.ParamSpecInterface.MSG_INVALID_PARAMETERS;
import static org.allsetconfigtest.TestingLevels.UNIT;
import static org.allsetconfigtest.combinatorial.CombinatorialTesting.combinationsWithValidValueAndInvalidValues;
import static org.allsetconfigtest.combinatorial.CombinatorialTesting.combinationsWithValidValues;
import static org.allsetconfigtest.combinatorial.CombinatorialTesting.combinationsWithValidValuesAndInvalidValues;
import static org.allsetconfigtest.combinatorial.ObjectCombinatorialParamSpec.ToType.ofObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.LinkedHashSet;
import java.util.stream.Stream;

import org.allsetconfigtest.combinatorial.ObjectCombinatorialParamSpec.ToType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class CombinatorialTestingTest {

    @DataProvider
    public Object[][] combinationsWithInvalidValuesAndValidValueExceptionData() {
	return new Object[][] { //
		{ new CombinatorialParamSpecInterface<?>[] {} }, //
		{ new CombinatorialParamSpecInterface<?>[] { null } } //
	};
    }

    @Test(priority = 1, groups = UNIT, dataProvider = "combinationsWithInvalidValuesAndValidValueExceptionData")
    public void combinationsWithInvalidValuesAndValidValueExceptionTest(final CombinatorialParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> combinationsWithValidValueAndInvalidValues(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);

    }

    @Test(priority = 2, groups = UNIT)
    public void combinationsWithInvalidValuesAndValidValueTest() {

	final var parameters = new CombinatorialParamSpecInterface[] { //
		ToType.<String>ofObject("Char") //
			.addValidValues("A", "B") //
			.addInvalidValues(null, ""), //

		ToType.<Integer>ofObject("Value") //
			.addValidValues(1, 2, 3) //
			.addInvalidValues(0, -1, -2, null), //
	};

	final var dataResult = combinationsWithValidValueAndInvalidValues(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.getInvalidValues().size()) //
		.distinct() //
		.reduce(0, (a, b) -> a + b);

	assertThat(parameters.length).isEqualTo(dataResult[1].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //
              // Char  , Value
                { ""   ,   1   }, //
                { null ,   1   }, //
                { "A"  ,   0   }, //
                { "A"  ,  -1   }, //
                { "A"  ,  -2   }, //
                { "A"  ,  null }, //
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    @DataProvider
    public Object[][] combinationsWithValidValuesAndInvalidValuesExceptionData() {
	return new Object[][] { //
		{ new CombinatorialParamSpecInterface[] {} }, //
		{ new CombinatorialParamSpecInterface[] { null } } //
	};
    }

    @Test(priority = 5, groups = UNIT, dataProvider = "combinationsWithValidValuesAndInvalidValuesExceptionData")
    public void combinationsWithValidValuesExceptionTest(final CombinatorialParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> combinationsWithValidValues(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);
    }

    @Test(priority = 6, groups = UNIT)
    public void combinationsWithValidValuesTest() {

	final var parameters = new CombinatorialParamSpecInterface[] { //
		ofObject("Char") //
			.addValidValues("A", "B") //
			.addInvalidValues(""), //

		ofObject("Value") //
			.addValidValues(1, 2, 3) //
			.addInvalidValues(0, -1, -2, null), //
	};

	final var dataResult = combinationsWithValidValues(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.getValidValues().size()) //
		.distinct() //
		.reduce(1, (a, b) -> a * b);

	assertThat(parameters.length).isEqualTo(dataResult[1].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //
              // Char  , Value
                { "A"  ,   1  }, //
                { "A"  ,   2  }, //
                { "A"  ,   3  }, //
                { "B"  ,   1  }, //
                { "B"  ,   2  },  //
                { "B"  ,   3  }  //
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    @Test(priority = 5, groups = UNIT, dataProvider = "combinationsWithValidValuesAndInvalidValuesExceptionData")
    public void combinationsWithValidValuesAndInvalidValuesExceptionTest(final CombinatorialParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> combinationsWithValidValuesAndInvalidValues(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);
    }

    @Test(priority = 6, groups = UNIT)
    public void combinationsWithValidValuesAndInvalidValuesTest() {

	final var parameters = new CombinatorialParamSpecInterface[] { //
		ofObject("Char") //
			.addValidValues("A", "B") //
			.addInvalidValues(""), //

		ofObject("Value") //
			.addValidValues(1, 2, 3) //
			.addInvalidValues(0, -1, -2, null), //
	};

	final var dataResult = combinationsWithValidValuesAndInvalidValues(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.getValues().size()) //
		.distinct() //
		.reduce(1, (a, b) -> a * b);

	assertThat(parameters.length).isEqualTo(dataResult[1].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //
              // Char  , Value
                { ""   ,   1  }, //
                { ""   ,   2  }, //
                { ""   ,   3  }, //
                { ""   ,   0  }, //
                { ""   ,  -1  }, //
                { ""   ,  -2  }, //
                { ""   ,  null}, //            
                
                { "A"  ,   1  }, //
                { "A"  ,   2  }, //
                { "A"  ,   3  }, //
                { "A"  ,   0  }, //
                { "A"  ,  -1  }, //
                { "A"  ,  -2  }, //
                { "A"  ,  null}, //
                
                { "B"  ,   1  }, //
                { "B"  ,   2  }, //
                { "B"  ,   3  }, //
                { "B"  ,   0  }, //
                { "B"  ,  -1  }, //
                { "B"  ,  -2  }, //
                { "B"  ,  null}, //
                
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }
}
