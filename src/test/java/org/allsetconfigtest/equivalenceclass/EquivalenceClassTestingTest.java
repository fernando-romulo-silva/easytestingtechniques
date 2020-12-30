package org.allsetconfigtest.equivalenceclass;

import static org.allsetconfigtest.ParamSpecInterface.MSG_INVALID_PARAMETERS;
import static org.allsetconfigtest.TestingLevels.UNIT;
import static org.allsetconfigtest.equivalenceclass.EquivalenceClass.newInvalidClass;
import static org.allsetconfigtest.equivalenceclass.EquivalenceClass.newValidClass;
import static org.allsetconfigtest.equivalenceclass.EquivalenceClassTesting.strongNormalEquivalenceClassTesting;
import static org.allsetconfigtest.equivalenceclass.EquivalenceClassTesting.strongRobustEquivalenceClassTesting;
import static org.allsetconfigtest.equivalenceclass.EquivalenceClassTesting.traditionalEquivalenceClassTesting;
import static org.allsetconfigtest.equivalenceclass.EquivalenceClassTesting.weakNormalEquivalenceClassTesting;
import static org.allsetconfigtest.equivalenceclass.EquivalenceClassTesting.weakRobustEquivalenceClassTesting;
import static org.allsetconfigtest.equivalenceclass.IntegerEquivalenceClassParamSpec.ofInteger;
import static org.allsetconfigtest.equivalenceclass.SpecRange.ofRange;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class EquivalenceClassTestingTest {

    // --------------------------- two parameters -----------------------------------------------------------------------------------------
    static final SpecRange<Integer> MONTH_RANGE = ofRange(-5, 15, 3);
    static final SpecRange<Integer> DAY_RANGE = ofRange(-5, 40, 3);
    static final SpecRange<Integer> YEAR_RANGE = ofRange(1900, 2030, 30);
    // age parameter
    // valids
    final Predicate<Integer> youngAge = p -> p.intValue() >= 18 && p.intValue() <= 30;
    final Predicate<Integer> adultAge = p -> p.intValue() >= 31 && p.intValue() <= 50;
    final Predicate<Integer> seniorAge = p -> p.intValue() >= 51 && p.intValue() <= 55;

    final Predicate<Integer> validsAge = youngAge.or(adultAge).or(seniorAge);

    // invalids
    final Predicate<Integer> underAge = p -> p.intValue() >= 0 && p.intValue() <= 17;
    final Predicate<Integer> oldAge = p -> p.intValue() >= 56;

    // Qtdy Product
    // valids
    final Predicate<Integer> qtProdRuleLittle = p -> p.intValue() >= 1 && p.intValue() <= 5;
    final Predicate<Integer> qtProdRuleMuch = p -> p.intValue() >= 6 && p.intValue() <= 10;

    final Predicate<Integer> qtProdValidRules = qtProdRuleLittle.or(qtProdRuleMuch);

    // invalids
    final Predicate<Integer> qtProdRuleUnder = p -> p.intValue() <= 0;
    final Predicate<Integer> qtProdRuleOver = p -> p.intValue() > 10;

    // --------------------------- three parameters -----------------------------------------------------------------------------------------
    static final SpecRange<Integer> QTY_PRODUCT_RANGE = ofRange(-1, 15, 2);
    static final SpecRange<Integer> AGE_RANGE = ofRange(-5, 130, 3);
    // month parameter
    // valids
    final Predicate<Integer> validMonth = p -> p.intValue() >= 1 && p.intValue() <= 12;
    final Predicate<Integer> validDay = p -> p.intValue() >= 1 && p.intValue() <= 31;

    final Predicate<Integer> validFirstTrimesterMonth = p -> p.intValue() >= 1 && p.intValue() <= 3;
    final Predicate<Integer> validSecondTrimesterMonth = p -> p.intValue() >= 4 && p.intValue() <= 6;
    final Predicate<Integer> validThirdTrimesterMonth = p -> p.intValue() >= 7 && p.intValue() <= 9;
    final Predicate<Integer> validFourthTrimesterMonth = p -> p.intValue() >= 10 && p.intValue() <= 12;

    // invalids
    final Predicate<Integer> invalidUnderMonth = p -> p.intValue() < 1;
    final Predicate<Integer> invalidOverMonth = p -> p.intValue() > 12;

    // day parameter
    // valids
    final Predicate<Integer> validFirstFortnight = p -> p.intValue() >= 1 && p.intValue() <= 15;
    final Predicate<Integer> validSecondFortnight = p -> p.intValue() >= 16 && p.intValue() <= 31;

    // invalids
    final Predicate<Integer> invalidUnderDay = p -> p.intValue() < 1;
    final Predicate<Integer> invalidOverDay = p -> p.intValue() > 31;

    // year parameter
    // valids
    final Predicate<Integer> validYear = p -> p.intValue() >= 1962 && p.intValue() <= 2025;
    // invalids
    final Predicate<Integer> invalidUnderYear = p -> p.intValue() < 1962;
    final Predicate<Integer> invalidOverYear = p -> p.intValue() > 2025;

    // --------------------------------------------------------------------------------------------------------------------------------------
    @DataProvider
    public Object[][] equivalenceClassTestingExceptionData() {
	return new Object[][] { //
		{ new EquivalenceClassParamSpecInterface<?>[] {} }, //
		{ new EquivalenceClassParamSpecInterface<?>[] { null } } //
	};
    }

    // --------------------------------------------------------------------------------------------------------------------------------------

    @Test(priority = 0, groups = UNIT, dataProvider = "equivalenceClassTestingExceptionData")
    public void traditionalEquivalenceClassTestingExceptionTest(final EquivalenceClassParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> traditionalEquivalenceClassTesting(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);

    }

    @SuppressWarnings("unchecked")
    @Test(priority = 1, groups = UNIT)
    public void traditionalEquivalenceClassTestingWithTwoParametersTest() {
	final var parameters = Arrays.array( //
		ofInteger("Age", AGE_RANGE) //
			.addClasses( //
				// valid classes
				newValidClass("Young", youngAge), //
				newValidClass("Adult", adultAge), //
				newValidClass("Senior", seniorAge), //

				// invalid classes
				newInvalidClass("Under", underAge), //
				newInvalidClass("Old", oldAge) //
			), //

		ofInteger("Qty Products", QTY_PRODUCT_RANGE) //
			.addClasses(//
				// valid classes
				newValidClass("Little", qtProdRuleLittle), //
				newValidClass("Much", qtProdRuleMuch), //

				// invalid classes
				newInvalidClass("Under", qtProdRuleUnder), //
				newInvalidClass("Over", qtProdRuleOver) //
			) //
	);

	final var dataResult = traditionalEquivalenceClassTesting(parameters);

	final var maxClasses = Stream.of(parameters) //
		.mapToInt(p -> p.getInvalidClasses().size()) //
		.sorted() //
		.max() //
		.getAsInt();

	final var qtyTestCase = parameters.length * maxClasses + 1;

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Predicate[][]{ //            
              //       Age      ,    Qtdy Product
                {   validsAge   ,  qtProdValidRules }, //
                
                {   underAge    ,  qtProdValidRules }, //
                {   oldAge      ,  qtProdValidRules }, //
                
                {   validsAge   ,  qtProdRuleUnder  }, //
                {   validsAge   ,  qtProdRuleOver   }, //
        };
        // @formatter:on

	for (var i = 0; i < dataResult.length; i++) {

	    final var line = dataResult[i];

	    for (var j = 0; j < line.length; j++) {

		final var rule = dataExpected[i][j];
		final var value = dataResult[i][j];

		assertThat(rule).accepts(value);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 2, groups = UNIT)
    public void traditionalEquivalenceClassTestingWithThreeParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Month", MONTH_RANGE) //
			.addValidClasses(validMonth)//
			.addInvalidClasses(invalidUnderMonth, invalidOverMonth), //

		ofInteger("Day", DAY_RANGE) //
			.addValidClasses(validDay)//
			.addInvalidClasses(invalidUnderDay, invalidOverDay), //

		ofInteger("Year", YEAR_RANGE) //
			.addValidClasses(validYear)//
			.addInvalidClasses(invalidUnderYear, invalidOverYear) //
	);

	final var dataResult = traditionalEquivalenceClassTesting(parameters);

	final var maxClasses = Stream.of(parameters) //
		.mapToInt(p -> p.getInvalidClasses().size()) //
		.sorted() //
		.max() //
		.getAsInt();

	final var qtyTestCase = parameters.length * maxClasses + 1;

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Predicate[][]{ //            
              //      Month         ,     Day           ,   Year
                { validMonth        ,  validDay         ,  validYear    }, // 1
                                                       
                { invalidUnderMonth ,  validDay         ,  validYear    }, // 2
                { invalidOverMonth  ,  validDay         ,  validYear    }, // 3
                 
                { validMonth        ,  invalidUnderDay  ,  validYear    }, // 4
                { validMonth        ,  invalidOverDay   ,  validYear    }, // 5                                      
                                                           
                { validMonth        ,  validDay         ,  invalidUnderYear }, // 6
                { validMonth        ,  validDay         ,  invalidOverYear }, // 7
                //  ---
        };
        // @formatter:on

	for (var i = 0; i < dataResult.length; i++) {

	    final var line = dataResult[i];

	    for (var j = 0; j < line.length; j++) {

		final var rule = dataExpected[i][j];
		final var value = dataResult[i][j];

		assertThat(rule).accepts(value);
	    }
	}
    }

    // --------------------------------------------------------------------------------------------------------------------------------------

    @Test(priority = 4, groups = UNIT, dataProvider = "equivalenceClassTestingExceptionData")
    public void weakNormalEquivalenceClassTestingExceptionTest(final EquivalenceClassParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> weakNormalEquivalenceClassTesting(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 5, groups = UNIT)
    public void weakNormalEquivalenceClassTestingWithTwoParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Age", AGE_RANGE) //
			.addClasses( //
				// valid classes
				newValidClass("Young", youngAge), //
				newValidClass("Adult", adultAge), //
				newValidClass("Senior", seniorAge), //

				// invalid classes
				newInvalidClass("Under", underAge), //
				newInvalidClass("Old", oldAge) //
			), //
		ofInteger("Qty Products", QTY_PRODUCT_RANGE) //
			.addClasses( //
				// valid classes
				newValidClass("Little", qtProdRuleLittle), //
				newValidClass("Much", qtProdRuleMuch), //

				// invalid classes
				newInvalidClass("Under", qtProdRuleUnder), //
				newInvalidClass("Over", qtProdRuleOver) //
			) //
	);

	final var dataResult = weakNormalEquivalenceClassTesting(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.getValidClasses().size()) //
		.sorted() //
		.max() //
		.getAsInt();

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Predicate[][]{ //            
              //       Age     ,    Qtdy Product
                {   youngAge   ,  qtProdRuleLittle }, //
                {   adultAge   ,  qtProdRuleMuch   }, //
                {   seniorAge  ,  qtProdRuleLittle }, //
        };
        // @formatter:on

	for (var i = 0; i < dataResult.length; i++) {

	    final var line = dataResult[i];

	    for (var j = 0; j < line.length; j++) {

		final var rule = dataExpected[i][j];
		final var value = dataResult[i][j];

		assertThat(rule).accepts(value);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 6, groups = UNIT)
    public void weakNormalEquivalenceClassTestingWithThreeParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Month", MONTH_RANGE) //

			.addValidClasses( //
				validFirstTrimesterMonth, //
				validSecondTrimesterMonth, //
				validThirdTrimesterMonth, //
				validFourthTrimesterMonth //
			)//

			.addInvalidClasses(invalidUnderMonth, invalidOverMonth), //

		ofInteger("Day", DAY_RANGE) //
			.addValidClasses(validFirstFortnight) //
			.addValidClasses(validSecondFortnight) //
			.addInvalidClasses(invalidUnderDay, invalidOverDay), //

		ofInteger("Year", YEAR_RANGE) //
			.addValidClasses(validYear)//
			.addInvalidClasses(invalidUnderYear, invalidOverYear) //
	);

	final var dataResult = weakNormalEquivalenceClassTesting(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.getValidClasses().size()) //
		.sorted() //
		.max() //
		.getAsInt();

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Predicate[][]{ //            
              //         Month              ,          Day         ,   Year
                { validFirstTrimesterMonth  ,  validFirstFortnight ,  validYear    }, // 1
                { validSecondTrimesterMonth ,  validSecondFortnight,  validYear    }, // 2
                { validThirdTrimesterMonth  ,  validFirstFortnight ,  validYear    }, // 3
                { validFourthTrimesterMonth ,  validSecondFortnight,  validYear    }, // 4
                
        };
        // @formatter:on

	for (var i = 0; i < dataResult.length; i++) {

	    final var line = dataResult[i];

	    for (var j = 0; j < line.length; j++) {

		final var rule = dataExpected[i][j];
		final var value = dataResult[i][j];

		assertThat(rule).accepts(value);
	    }
	}
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------

    @Test(priority = 7, groups = UNIT, dataProvider = "equivalenceClassTestingExceptionData")
    public void strongNormalEquivalenceClassTestingExceptionTest(final EquivalenceClassParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> strongNormalEquivalenceClassTesting(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 8, groups = UNIT)
    public void strongNormalEquivalenceClassTestingWithTwoParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Age", AGE_RANGE) //
			.addClasses(
				// valid classes
				newValidClass("Young", youngAge), //
				newValidClass("Adult", adultAge), //
				newValidClass("Senior", seniorAge), //

				// invalid classes
				newInvalidClass("Under", underAge), //
				newInvalidClass("Old", oldAge) //
			), //
		ofInteger("Qty Products", QTY_PRODUCT_RANGE) //
			.addClasses(
				// valid classes
				newValidClass("Little", qtProdRuleLittle), //
				newValidClass("Much", qtProdRuleMuch), //

				// invalid classes
				newInvalidClass("Under", qtProdRuleUnder), //
				newInvalidClass("Over", qtProdRuleOver) //
			) //
	);

	final var dataResult = strongNormalEquivalenceClassTesting(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.getValidClasses().size()) //
		.distinct() //
		.reduce(1, (a, b) -> a * b);

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Predicate[][]{ //            
              //       Age     ,    Qtdy Product           
                {   youngAge   ,  qtProdRuleLittle }, // 1
                {   youngAge   ,  qtProdRuleMuch   }, // 2
                
                {   adultAge   ,  qtProdRuleLittle }, // 3
                {   adultAge   ,  qtProdRuleMuch   }, // 4
                
                {   seniorAge  ,  qtProdRuleLittle }, // 5
                {   seniorAge  ,  qtProdRuleMuch   }, // 6
        };
        // @formatter:on

	for (var i = 0; i < dataResult.length; i++) {

	    final var line = dataResult[i];

	    for (var j = 0; j < line.length; j++) {

		final var rule = dataExpected[i][j];
		final var value = dataResult[i][j];

		assertThat(rule).accepts(value);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 9, groups = UNIT)
    public void strongNormalEquivalenceClassTestingWithThreeParametersTest() {

	final var parameters = Arrays.array( //
		ofInteger("Month", MONTH_RANGE) //

			.addValidClasses( //
				validFirstTrimesterMonth, //
				validSecondTrimesterMonth, //
				validThirdTrimesterMonth, //
				validFourthTrimesterMonth //
			)//
			.addInvalidClasses(invalidUnderMonth, invalidOverMonth), //

		ofInteger("Day", DAY_RANGE) //
			.addValidClasses(validFirstFortnight) //
			.addValidClasses(validSecondFortnight) //
			.addInvalidClasses(invalidUnderDay, invalidOverDay), //

		ofInteger("Year", YEAR_RANGE) //
			.addValidClasses(validYear)//
			.addInvalidClasses(invalidUnderYear, invalidOverYear) //
	);

	final var dataResult = strongNormalEquivalenceClassTesting(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.getValidClasses().size()) //
		.distinct() //
		.reduce(1, (a, b) -> a * b);

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Predicate[][]{ //            
              //             Month          ,          Day         ,   Year
                { validFirstTrimesterMonth  ,  validFirstFortnight ,  validYear    }, // 1
                { validFirstTrimesterMonth  ,  validSecondFortnight,  validYear    }, // 2
                
                { validSecondTrimesterMonth ,  validFirstFortnight ,  validYear    }, // 3
                { validSecondTrimesterMonth ,  validSecondFortnight,  validYear    }, // 4
                
                { validThirdTrimesterMonth  ,  validFirstFortnight ,  validYear    }, // 5
                { validThirdTrimesterMonth  ,  validSecondFortnight,  validYear    }, // 6
                
                { validFourthTrimesterMonth ,  validFirstFortnight ,  validYear    }, // 7
                { validFourthTrimesterMonth ,  validSecondFortnight,  validYear    }, // 8
                
        };
        // @formatter:on

	for (var i = 0; i < dataResult.length; i++) {

	    final var line = dataResult[i];

	    for (var j = 0; j < line.length; j++) {

		final var rule = dataExpected[i][j];
		final var value = dataResult[i][j];

		assertThat(rule).accepts(value);
	    }
	}
    }
    // --------------------------------------------------------------------------------------------------------------------------------------

    @Test(priority = 10, groups = UNIT, dataProvider = "equivalenceClassTestingExceptionData")
    public void weakRobustEquivalenceClassTestingExceptionTest(final EquivalenceClassParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> weakRobustEquivalenceClassTesting(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 11, groups = UNIT)
    public void weakRobustEquivalenceClassTestingWithTwoParametersTest() {

	final var parameters = Arrays.array( //

		ofInteger("Age", AGE_RANGE) //
			.addClasses( //
				// valid classes
				newValidClass("Young", youngAge), //
				newValidClass("Adult", adultAge), //
				newValidClass("Senior", seniorAge), //

				// invalid classes
				newInvalidClass("Under", underAge), //
				newInvalidClass("Old", oldAge) //
			), //

		ofInteger("Qty Products", ofRange(-3, 15, 2)) //
			.addClasses( //
				// valid classes
				newValidClass("Little", qtProdRuleLittle), //
				newValidClass("Much", qtProdRuleMuch), //

				// invalid classes
				newInvalidClass("Under", qtProdRuleUnder), //
				newInvalidClass("Over", qtProdRuleOver) //
			) //
	);

	final var dataResult = weakRobustEquivalenceClassTesting(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.getClasses().size()) //
		.sorted() //
		.max() //
		.getAsInt();

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Predicate[][]{ //            
              //       Age      ,    Qtdy Product
                {  underAge     ,   qtProdRuleUnder }, // 1
                {    oldAge     ,   qtProdRuleOver  }, // 2               
                {   youngAge    ,  qtProdRuleLittle }, // 3
                {   adultAge    ,  qtProdRuleMuch   }, // 4
                {   seniorAge   ,  qtProdRuleLittle }, // 5
        };
        // @formatter:on

	for (var i = 0; i < dataResult.length; i++) {

	    final var line = dataResult[i];

	    for (var j = 0; j < line.length; j++) {

		final var rule = dataExpected[i][j];
		final var value = dataResult[i][j];

		assertThat(rule).accepts(value);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 12, groups = UNIT)
    public void weakRobustEquivalenceClassTestingWithThreeParametersTest() {

	final var parameters = Arrays.array( //

		ofInteger("Month", MONTH_RANGE) //
			.addValidClasses( //
				validFirstTrimesterMonth, //
				validSecondTrimesterMonth, //
				validThirdTrimesterMonth, //
				validFourthTrimesterMonth //
			)//

			.addInvalidClasses(invalidUnderMonth, invalidOverMonth), //

		ofInteger("Day", DAY_RANGE) //
			.addValidClasses(validFirstFortnight) //
			.addValidClasses(validSecondFortnight) //
			.addInvalidClasses(invalidUnderDay, invalidOverDay), //

		ofInteger("Year", YEAR_RANGE) //
			.addValidClasses(validYear)//
			.addInvalidClasses(invalidUnderYear, invalidOverYear) //

	);

	final var dataResult = weakRobustEquivalenceClassTesting(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.getClasses().size()) //
		.sorted() //
		.max() //
		.getAsInt();

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Predicate[][]{ //            
              //             Month          ,          Day         ,   Year
                {     invalidUnderMonth     ,     invalidUnderDay  ,  invalidUnderYear }, // 1
                {     invalidOverMonth      ,     invalidOverDay   ,  invalidOverYear  }, // 2
            
                { validFirstTrimesterMonth  ,  validFirstFortnight ,  validYear        }, // 3
                { validSecondTrimesterMonth ,  validSecondFortnight,  validYear        }, // 4
                { validThirdTrimesterMonth  ,  validFirstFortnight ,  validYear        }, // 5
                { validFourthTrimesterMonth ,  validSecondFortnight,  validYear        }, // 6
                
        };
        // @formatter:on

	for (var i = 0; i < dataResult.length; i++) {

	    final var line = dataResult[i];

	    for (var j = 0; j < line.length; j++) {

		final var rule = dataExpected[i][j];
		final var value = dataResult[i][j];

		assertThat(rule).accepts(value);
	    }
	}
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------

    @Test(priority = 13, groups = UNIT, dataProvider = "equivalenceClassTestingExceptionData")
    public void strongRobustEquivalenceClassTestingExceptionTest(final EquivalenceClassParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> strongRobustEquivalenceClassTesting(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 14, groups = UNIT)
    public void strongRobustEquivalenceClassTestingWithTwoParametersTest() {

	final var parameters = Arrays.array( //

		ofInteger("Age", AGE_RANGE) //
			.addClasses( //
				// valid classes
				newValidClass("Young", youngAge), //
				newValidClass("Adult", adultAge), //
				newValidClass("Senior", seniorAge), //

				// invalid classes
				newInvalidClass("Under", underAge), //
				newInvalidClass("Old", oldAge) //
			), //

		ofInteger("Qty Products", QTY_PRODUCT_RANGE) //
			.addClasses( // 
				// valid classes
				newValidClass("Little", qtProdRuleLittle), //
				newValidClass("Much", qtProdRuleMuch), //

				// invalid classes
				newInvalidClass("Under", qtProdRuleUnder), //
				newInvalidClass("Over", qtProdRuleOver) //
			) //
	);

	final var dataResult = strongRobustEquivalenceClassTesting(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.getClasses().size()) //
		.distinct() //
		.reduce(1, (a, b) -> a * b);

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
        final var dataExpected = new Predicate[][]{ //            
              //       Age     ,    Qty Products
                {    underAge  ,  qtProdRuleUnder  }, // 1
                {    underAge  ,  qtProdRuleOver   }, // 2                
            	{    underAge  ,  qtProdRuleLittle }, // 3
            	{    underAge  ,  qtProdRuleMuch   }, // 4
            	
                {    oldAge    ,  qtProdRuleUnder  }, // 5
                {    oldAge    ,  qtProdRuleOver   }, // 6               
            	{    oldAge    ,  qtProdRuleLittle }, // 7
            	{    oldAge    ,  qtProdRuleMuch   }, // 8
       	
                {   youngAge   ,  qtProdRuleUnder  }, // 9
                {   youngAge   ,  qtProdRuleOver   }, // 10               
                {   youngAge   ,  qtProdRuleLittle }, // 11
                {   youngAge   ,  qtProdRuleMuch   }, // 12
                
                {   adultAge   ,  qtProdRuleUnder  }, // 13
                {   adultAge   ,  qtProdRuleOver   }, // 14               
                {   adultAge   ,  qtProdRuleLittle }, // 15
                {   adultAge   ,  qtProdRuleMuch   }, // 16
                
                {   seniorAge  ,  qtProdRuleUnder  }, // 17
                {   seniorAge  ,  qtProdRuleOver   }, // 18                 
                {   seniorAge  ,  qtProdRuleLittle }, // 19
                {   seniorAge  ,  qtProdRuleMuch   }, // 20
        };
        // @formatter:on

	for (var i = 0; i < dataResult.length; i++) {

	    final var line = dataResult[i];

	    for (var j = 0; j < line.length; j++) {

		final var rule = dataExpected[i][j];
		final var value = dataResult[i][j];

		assertThat(rule).accepts(value);
	    }
	}
    }

    @Test(priority = 15, groups = UNIT)
    public void strongRobustEquivalenceClassTestingWithThreeParametersTest() {

	final var parameters = Arrays.array( //

		ofInteger("Month", MONTH_RANGE) //

			.addValidClasses( //
				validFirstTrimesterMonth, //
				validSecondTrimesterMonth, //
				validThirdTrimesterMonth, //
				validFourthTrimesterMonth //
			)//

			.addInvalidClasses(invalidUnderMonth, invalidOverMonth), //

		ofInteger("Day", DAY_RANGE) //
			.addValidClasses(validFirstFortnight) //
			.addValidClasses(validSecondFortnight) //

			.addInvalidClasses(invalidUnderDay, invalidOverDay), //

		ofInteger("Year", YEAR_RANGE) //
			.addValidClasses(validYear)//
			.addInvalidClasses(invalidUnderYear, invalidOverYear) //
	);

	final var dataResult = strongRobustEquivalenceClassTesting(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.getClasses().size()) //
		.distinct() //
		.reduce(1, (a, b) -> a * b);

	assertThat(parameters.length).isEqualTo(dataResult[0].length);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	// @formatter:off
	@SuppressWarnings("unused")
	final var dataExpected = new Predicate[][]{ //            
              //             Month          ,          Day         ,   Year
                { validFirstTrimesterMonth  ,  validFirstFortnight ,  validYear    }, // 1
                { validFirstTrimesterMonth  ,  validSecondFortnight,  validYear    }, // 2
                
                { validSecondTrimesterMonth ,  validFirstFortnight ,  validYear    }, // 3
                { validSecondTrimesterMonth ,  validSecondFortnight,  validYear    }, // 4
                
                { validThirdTrimesterMonth  ,  validFirstFortnight ,  validYear    }, // 5
                { validThirdTrimesterMonth  ,  validSecondFortnight,  validYear    }, // 6
                
                { validFourthTrimesterMonth ,  validFirstFortnight ,  validYear    }, // 7
                { validFourthTrimesterMonth ,  validSecondFortnight,  validYear    }, // 8
                
        };
        // @formatter:on

	for (var i = 0; i < dataResult.length; i++) {

	    final var line = dataResult[i];

	    for (var j = 0; j < line.length; j++) {

		// final var rule = dataExpected[i][j];
		// final var value = dataResult[i][j];

		// assertThat(rule).accepts(value);
	    }
	}
    }
}
