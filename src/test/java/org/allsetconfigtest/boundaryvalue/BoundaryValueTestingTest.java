package org.allsetconfigtest.boundaryvalue;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.allsetconfigtest.TestingLevels.UNIT;
import static org.allsetconfigtest.boundaryvalue.BoundaryValueTesting.normalBoundaryValueTesting;
import static org.allsetconfigtest.boundaryvalue.BoundaryValueTesting.robustBoundaryValueTesting;
import static org.allsetconfigtest.boundaryvalue.BoundaryValueTesting.worstNormalBoundaryValueTesting;
import static org.allsetconfigtest.boundaryvalue.BoundaryValueTesting.worstRobustBoundaryValueTesting;
import static org.allsetconfigtest.boundaryvalue.IntegerBoundaryParamSpec.ofInteger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.allsetconfigtest.ParamSpecInterface.MSG_INVALID_PARAMETERS;

import java.util.LinkedHashSet;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class BoundaryValueTestingTest {

    // --------------------------------------------------------------------------------------------------------------------------------------
    @DataProvider
    public Object[][] boundaryValueTestingExceptionData() {
	return new Object[][] { //
		{ new BoundaryValueParamSpecInterface<?>[] {} }, //
		{ new BoundaryValueParamSpecInterface<?>[] { null } } //
	};
    }

    // --------------------------------------------------------------------------------------------------------------------------------------

    @Test(priority = 0, groups = UNIT, dataProvider = "boundaryValueTestingExceptionData")
    public void normalBoundaryValueTestingExceptionTest(final BoundaryValueParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> normalBoundaryValueTesting(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);
    }

    @Test(priority = 1, groups = UNIT)
    public void normalBoundaryValueTestingWithTwoParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Age", 30, 18, 55), //
		ofInteger("Qty Products", 5, 1, 10) //
	);

	final var dataResult = normalBoundaryValueTesting(parameters);

	final var qtyTestCase = parameters.length * 4 + 1;

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //            
              // Input X , Input Y
                {   30   ,   1   }, //
                {   30   ,   2   }, //
                {   30   ,   9   }, //
                {   30   ,   10  }, //
                {   30   ,   5   }, //
                {   18   ,   5   }, //
                {   19   ,   5   }, //
                {   54   ,   5   }, //
                {   55   ,   5   }, //
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    @Test(priority = 2, groups = UNIT)
    public void normalBoundaryValueTestingWithThreeParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Month", 6, 1, 12), //
		ofInteger("Day", 15, 1, 31), //
		ofInteger("Year", 1962, 1900, 2025) //
	);

	final var dataResult = normalBoundaryValueTesting(parameters);
	final var qtyTestCase = parameters.length * 4 + 1;

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //            
              // Month  ,  Day  , Year
                {   6   ,   15  ,  1900  }, // 1
                {   6   ,   15  ,  1901  }, // 2
                {   6   ,   15  ,  1962  }, // 3
                {   6   ,   15  ,  2024  }, // 4
                {   6   ,   15  ,  2025  }, // 5
                //                 ---- 
                
                {   6   ,   1   ,  1962  }, // 6
                {   6   ,   2   ,  1962  }, // 7
                {   6   ,   30  ,  1962  }, // 8
                {   6   ,   31  ,  1962  }, // 9
                //         ---    
                
                {   1   ,   15  ,  1962  }, // 10                
                {   2   ,   15  ,  1962  }, // 11
                {   11  ,   15  ,  1962  }, // 12
                {   12  ,   15  ,  1962  }, // 13
                //  ---
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    @Test(priority = 3, groups = UNIT, enabled = false)
    public void normalBoundaryValueTestingDiferentParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Gender", 1, 1, 0), // 0 Male, 1 Female
		ofInteger("Age", 25, 18, 55), //
		ofInteger("Salary", 5000, 0, 10000) //
	);

	final var dataResult = normalBoundaryValueTesting(parameters);

	final var qtyTestCase = parameters.length * 4 + 1;

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //            
              // Gender ,   Age , Salary
                {   0   ,   25  ,  1     }, //
                {   0   ,   25  ,  2     }, //
                {   0   ,   25  ,  5000  }, //
                {   0   ,   25  ,  9999  }, //
                {   0   ,   25  ,  10000 }, //
                //                  ---
                
                {   0   ,   18  ,  5000   }, //
                {   0   ,   19  ,  5000   }, //
                {   0   ,   25  ,  5000   }, //
                {   0   ,   54  ,  5000   }, //
                {   0   ,   55  ,  5000   }, //
                //          ---
                
                {   0   ,   25  ,  5000   }, //
                {   1   ,   25  ,  5000   }, //
                // ---                
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    // --------------------------------------------------------------------------------------------------------------------------------------

    @Test(priority = 4, groups = UNIT, dataProvider = "boundaryValueTestingExceptionData")
    public void worstNormalBoundaryValueTestingExceptionTest(final BoundaryValueParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> worstNormalBoundaryValueTesting(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);
    }

    @Test(priority = 5, groups = UNIT)
    public void worstNormalBoundaryValueTestingWithTwoParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Age", 30, 18, 55), //
		ofInteger("Qty Products", 5, 1, 10) //
	);

	final var dataResult = worstNormalBoundaryValueTesting(parameters);

	final var qtyTestCase = Math.pow(5, parameters.length);

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //            
              //   Age   , Qty Product
                {   18   ,   1   }, //
                {   18   ,   2   }, //
                {   18   ,   5   }, //
                {   18   ,   9   }, //
                {   18   ,   10  }, //
                
                {   19   ,   1   }, //
                {   19   ,   2   }, //
                {   19   ,   5   }, //
                {   19   ,   9   }, //
                {   19   ,   10  }, //                
                
                {   30   ,   1   }, //
                {   30   ,   2   }, //
                {   30   ,   5   }, //
                {   30   ,   9   }, //
                {   30   ,   10  }, //                
                
                {   54   ,   1   }, //
                {   54   ,   2   }, //
                {   54   ,   5   }, //
                {   54   ,   9   }, //
                {   54   ,   10  }, //                
                
                {   55   ,   1   }, //
                {   55   ,   2   }, //
                {   55   ,   5   }, //
                {   55   ,   9   }, //
                {   55   ,   10  }, //                
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    @Test(priority = 6, groups = UNIT)
    public void worstNormalBoundaryValueTestingWithThreeParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Month", 6, 1, 12), //
		ofInteger("Day", 15, 1, 31), //
		ofInteger("Year", 1962, 1900, 2025) //
	);

	final var dataResult = worstNormalBoundaryValueTesting(parameters);

	final var qtyTestCase = Math.pow(5, parameters.length);

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //            
              // Month  ,  Day  , Year
                {   6   ,   15  ,  1900  }, // 1
                {   6   ,   15  ,  1901  }, // 2
                {   6   ,   15  ,  1962  }, // 3
                {   6   ,   15  ,  2024  }, // 4
                {   6   ,   15  ,  2025  }, // 5
                //                 ---- 
                
                {   6   ,   1   ,  1962  }, // 6
                {   6   ,   2   ,  1962  }, // 7
                {   6   ,   30  ,  1962  }, // 8
                {   6   ,   31  ,  1962  }, // 9
                //         ---    
                
                {   1   ,   15  ,  1962  }, // 10                
                {   2   ,   15  ,  1962  }, // 11
                {   11  ,   15  ,  1962  }, // 12
                {   12  ,   15  ,  1962  }, // 13
                //  ---
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    // --------------------------------------------------------------------------------------------------------------------------------------

    @Test(priority = 7, groups = UNIT, dataProvider = "boundaryValueTestingExceptionData")
    public void robustBoundaryValueTestingExceptionTest(final BoundaryValueParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> robustBoundaryValueTesting(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);
    }

    @Test(priority = 8, groups = UNIT)
    public void robustBoundaryValueTestingTwoParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Age", 30, 18, 55), //
		ofInteger("Qty Products", 5, 1, 10) //
	);

	final var dataResult = robustBoundaryValueTesting(parameters);

	final var qtyTestCase = 6 * parameters.length + 1;

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //            
              // Input X , Input Y
                {   30   ,   0   }, //
                {   30   ,   1   }, //
                {   30   ,   2   }, //
                {   30   ,   9   }, //
                {   30   ,   10  }, //
                {   30   ,   11  }, //
                
                {   30   ,   5   }, //
                
                {   17   ,   5   }, //
                {   18   ,   5   }, //
                {   19   ,   5   }, //
                {   54   ,   5   }, //
                {   55   ,   5   }, //
                {   56   ,   5   }, //
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    @Test(priority = 9, groups = UNIT)
    public void robustBoundaryValueTestingWithThreeParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Month", 6, 1, 12), //
		ofInteger("Day", 15, 1, 31), //
		ofInteger("Year", 1962, 1900, 2025) //
	);

	final var dataResult = robustBoundaryValueTesting(parameters);

	final var qtyTestCase = 6 * parameters.length + 1;

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //            
              // Month  ,  Day  , Year
                {   6   ,   15  ,  1899  }, // 1
                {   6   ,   15  ,  1900  }, // 2
                {   6   ,   15  ,  1901  }, // 3
                {   6   ,   15  ,  1962  }, // 4
                {   6   ,   15  ,  2024  }, // 5
                {   6   ,   15  ,  2025  }, // 6
                {   6   ,   15  ,  2026  }, // 7
                //                 ---- 
                
                {   6   ,   0   ,  1962  }, // 8
                {   6   ,   1   ,  1962  }, // 9
                {   6   ,   2   ,  1962  }, // 10
                {   6   ,   30  ,  1962  }, // 11
                {   6   ,   31  ,  1962  }, // 12
                {   6   ,   32  ,  1962  }, // 13
                //         ---    
                
                {   0   ,   15  ,  1962  }, // 14
                {   1   ,   15  ,  1962  }, // 15                
                {   2   ,   15  ,  1962  }, // 16
                {   11  ,   15  ,  1962  }, // 17
                {   12  ,   15  ,  1962  }, // 18
                {   13  ,   15  ,  1962  }, // 19
                //  ---
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    @Test(priority = 10, groups = UNIT, enabled = false)
    public void robustBoundaryValueTestingDiferentParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Gender", 1, 1, 0), // 0 Male, 1 Female
		ofInteger("Age", 25, 18, 55), //
		ofInteger("Salary", 5000, 0, 10000) //
	);

	final var dataResult = robustBoundaryValueTesting(parameters);

	final var qtyTestCase = 6 * parameters.length + 1;

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //            
              // Input X, Input Y, Input Z
                {   0   ,   25  ,  1     }, //
                {   0   ,   25  ,  2     }, //
                {   0   ,   25  ,  5000  }, //
                {   0   ,   25  ,  9999  }, //
                {   0   ,   25  ,  10000 }, //
                //                  ---
                
                {   0   ,   18  ,  5000   }, //
                {   0   ,   19  ,  5000   }, //
                {   0   ,   25  ,  5000   }, //
                {   0   ,   54  ,  5000   }, //
                {   0   ,   55  ,  5000   }, //
                //          ---
                
                {   0   ,   25  ,  5000   }, //
                {   1   ,   25  ,  5000   }, //
                // ---                
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    // --------------------------------------------------------------------------------------------------------------------------------------

    @Test(priority = 11, groups = UNIT, dataProvider = "boundaryValueTestingExceptionData")
    public void worstRobustBoundaryValueTestingExceptionTest(final BoundaryValueParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> worstRobustBoundaryValueTesting(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);
    }

    @Test(priority = 12, groups = UNIT)
    public void worstRobustBoundaryValueTestingWithTwoParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Age", 30, 18, 55), //
		ofInteger("Qty Products", 5, 1, 10) //
	);

	final var dataResult = worstRobustBoundaryValueTesting(parameters);

	final var qtyTestCase = Math.pow(7, parameters.length);

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //            
              //   Age   , Qty Product
                {   17   ,   0   }, //
                {   17   ,   1   }, //
                {   17   ,   2   }, //
                {   17   ,   5   }, //
                {   17   ,   9   }, //
                {   17   ,   10  }, //
                {   17   ,   11  }, //  
                
                {   18   ,   0   }, //
                {   18   ,   1   }, //
                {   18   ,   2   }, //
                {   18   ,   5   }, //
                {   18   ,   9   }, //
                {   18   ,   10  }, //
                {   18   ,   11  }, //  
                
                {   19   ,   0   }, //
                {   19   ,   1   }, //
                {   19   ,   2   }, //
                {   19   ,   5   }, //
                {   19   ,   9   }, //
                {   19   ,   10  }, //
                {   19   ,   11  }, //
                
                {   30   ,   0   }, //
                {   30   ,   1   }, //
                {   30   ,   2   }, //
                {   30   ,   5   }, //
                {   30   ,   9   }, //
                {   30   ,   10  }, //
                {   30   ,   11  }, //
                
                {   54   ,   0   }, //
                {   54   ,   1   }, //
                {   54   ,   2   }, //
                {   54   ,   5   }, //
                {   54   ,   9   }, //
                {   54   ,   10  }, //
                {   54   ,   11  }, //
                
                {   55   ,   0   }, //
                {   55   ,   1   }, //
                {   55   ,   2   }, //
                {   55   ,   5   }, //
                {   55   ,   9   }, //
                {   55   ,   10  }, //
                {   55   ,   11  }, //
                
                {   56   ,   0   }, //
                {   56   ,   1   }, //
                {   56   ,   2   }, //
                {   56   ,   5   }, //
                {   56   ,   9   }, //
                {   56   ,   10  }, //
                {   56   ,   11  }, //
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

    @Test(priority = 13, groups = UNIT)
    public void worstRobustBoundaryValueTestingWithThreeParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Month", 6, 1, 12), //
		ofInteger("Day", 15, 1, 31), //
		ofInteger("Year", 1962, 1900, 2025) //
	);

	final var dataResult = worstRobustBoundaryValueTesting(parameters);

	final var qtyTestCase = Math.pow(7, parameters.length);

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Object[][]{ //            
              // Month  ,  Day  , Year
                {   6   ,   15  ,  1900  }, // 1
                {   6   ,   15  ,  1901  }, // 2
                {   6   ,   15  ,  1962  }, // 3
                {   6   ,   15  ,  2024  }, // 4
                {   6   ,   15  ,  2025  }, // 5
                //                 ---- 
                
                {   6   ,   1   ,  1962  }, // 6
                {   6   ,   2   ,  1962  }, // 7
                {   6   ,   30  ,  1962  }, // 8
                {   6   ,   31  ,  1962  }, // 9
                //         ---    
                
                {   1   ,   15  ,  1962  }, // 10                
                {   2   ,   15  ,  1962  }, // 11
                {   11  ,   15  ,  1962  }, // 12
                {   12  ,   15  ,  1962  }, // 13
                //  ---
        };
        // @formatter:on

	final var dataExpectedList = Stream.of(dataExpected).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());
	final var dataResultList = Stream.of(dataResult).map(l -> new LinkedHashSet<>(asList(l))).collect(toList());

	for (var i = 0; i < dataExpected.length; i++) {
	    assertThat(dataExpectedList).contains(dataResultList.get(0));
	}
    }

}
