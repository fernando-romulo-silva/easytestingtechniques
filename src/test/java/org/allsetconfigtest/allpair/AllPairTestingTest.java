package org.allsetconfigtest.allpair;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static org.allsetconfigtest.ParamSpecInterface.MSG_INVALID_PARAMETERS;
import static org.allsetconfigtest.TestingLevels.UNIT;
import static org.allsetconfigtest.allpair.AllPairTesting.allPairValues;
import static org.allsetconfigtest.allpair.ObjectAllPairParamSpec.ToType.ofObject; 
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.allsetconfigtest.allpair.ObjectAllPairParamSpec.ToType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class AllPairTestingTest {

    @DataProvider
    public Object[][] allPairExceptionData() {
	return new Object[][] { //
		{ new AllPairParamSpecInterface<?>[] {} }, //
		{ new AllPairParamSpecInterface<?>[] { null } } //
	};
    }

    @Test(priority = 0, groups = UNIT, dataProvider = "allPairExceptionData")
    public void allPairValuesExceptionTest(final AllPairParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> allPairValues(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);
    }

    @Test(priority = 1, groups = UNIT)
    public void allPairValuesWithThreeParametersAndTwoValuesTest() {

	final var parameters = Arrays.array( //
		ToType.<Integer>ofObject("ImputX").addValues(1, 2), //
		ToType.<String>ofObject("ImputY").addValues("Q", "R"), //
		ToType.<Integer>ofObject("ImputZ").addValues(5, 6) //
	);//

	final var dataResult = allPairValues(parameters);

	final var qtTest = calculateQtyTestCaseForAllPairValues(parameters);

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtTest).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //            
              // Input X, Input Y, Input Z
                {   1   ,   "Q"  ,    5   }, // 1
                {   1   ,   "R"  ,    6   }, // 2
                {   2   ,   "Q"  ,    6   }, // 3
                {   2   ,   "R"  ,    5   }, // 4
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    @Test(priority = 2, groups = UNIT)
    public void allPairValuesWithThreeParameterAndVariousValuesTest() {
	final var parameters = Arrays.array( //
		ofObject("Enabled").addValues(TRUE, FALSE), //
		ofObject("Type").addValues(1, 2, 3), //
		ofObject("Category").addValues("a", "b", "c", "d") //
	);

	final var dataResult = allPairValues(parameters);

	final var qtTest = calculateQtyTestCaseForAllPairValues(parameters);

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtTest).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected01 = new Object[][]{ //            
              // Enabled,   Type , Category
                {  TRUE	,    1   ,   "a"  },  // 1 
                {  FALSE,    2   ,   "a"  },  // 2
                {  TRUE	,    3   ,   "a"  },  // 3
                                              
                {  FALSE,    1   ,   "b"  },  // 4
                {  TRUE	,    2   ,   "b"  },  // 5
                {  FALSE,    3   ,   "b"  },  // 6
                                              
                {  TRUE	,    1   ,   "c"  },  // 7
                {  FALSE,    2   ,   "c"  },  // 8
                {  TRUE	,    3   ,   "c"  },  // 9
                                              
                {  FALSE,    1   ,   "d"  },  // 10
                {  TRUE ,    2   ,   "d"  },  // 11
                {  FALSE,    3   ,   "d"  },  // 12
                
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected01).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected01.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    private int calculateQtyTestCaseForAllPairValues(final AllPairParamSpecInterface<?>[] parameters) {
	final var valuesSize = Stream.of(parameters) //
		.map(p -> p.getValues().size()) //
		.sorted() //
		.collect(toCollection(ArrayList::new));

	final var size = valuesSize.size();

	return size > 2 ? valuesSize.get(size - 1) * valuesSize.get(size - 2) : valuesSize.get(0);
    }
}
