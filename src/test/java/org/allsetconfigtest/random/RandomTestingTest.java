package org.allsetconfigtest.random;

import static org.allsetconfigtest.ParamSpecInterface.MSG_INVALID_PARAMETERS;
import static org.allsetconfigtest.TestingLevels.UNIT;
import static org.allsetconfigtest.random.IntegerRandomParamSpec.ofInteger;
import static org.allsetconfigtest.random.RandomTesting.randomTesting;
import static org.allsetconfigtest.random.StringRandomParamSpec.ofString;
import static org.apache.commons.lang3.StringUtils.length;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class RandomTestingTest {

    // --------------------------------------------------------------------------------------------------------------------------------------
    @DataProvider
    public Object[][] randomTestingExceptionData() {
	return new Object[][] { //
		{ new RandomParamSpecInterface<?>[] {} }, //
		{ new RandomParamSpecInterface<?>[] { null } } //
	};
    }

    // --------------------------------------------------------------------------------------------------------------------------------------

    @Test(priority = 0, groups = UNIT, dataProvider = "randomTestingExceptionData")
    public void randomTestingExceptionTest(final RandomParamSpecInterface<?>[] parameters) {

	assertThatThrownBy(() -> randomTesting(parameters)) //
		.isInstanceOf(IllegalArgumentException.class) //
		.hasMessage(MSG_INVALID_PARAMETERS);

    }

    @DataProvider
    public Object[][] randomTestingWithTwoParametersData() {
	// @formatter:off
	return new Object[][] { //
	    // qtyMinValue, qtyMaxValue, nameMaxLength, nameMinLength, nameNumbers, nameLowerLetters, nameUpperLetters, nameSpecialChar
	      { 1         ,  20        ,  5           ,  4           ,  false     ,  true           ,  false          ,  false         }, //
	      { 40        ,  100       ,  10          ,  10          ,  true      ,  false          ,  true           ,  false         }, //
	      { -15       ,  0         ,  100         ,  50          ,  false     ,  false          ,  true           ,  false         }, //
	};
        // @formatter:on
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 1, groups = UNIT, dataProvider = "randomTestingWithTwoParametersData")
    public void randomTestingWithTwoParametersTest( //
	    // param qty
	    final int qtyMinValue, //
	    final int qtyMaxValue, //
	    // param name
	    final int nameMaxLength, //
	    final int nameMinLength, //
	    final boolean nameNumbers, //
	    final boolean nameLowerLetters, //
	    final boolean nameUpperLetters, //
	    final boolean nameSpecialChar //
    ) {

	final var parameters = Arrays.array( //

		ofString("Name", 5, $ -> {
		    $.maxLength = nameMaxLength;
		    $.minLength = nameMinLength;
		    $.numbers = nameNumbers;
		    $.specialChar = nameSpecialChar;
		    //
		    $.lowerLetters = nameLowerLetters;
		    $.upperLetters = nameUpperLetters;
		}), //

		ofInteger("Qty Product", qtyMinValue, qtyMaxValue) //
	);

	final var dataResult = randomTesting(parameters);

	final var qtyTestCase = Stream.of(parameters) //
		.mapToInt(p -> p.qtyValues) //
		.max() //
		.getAsInt();

	assertThat(parameters).hasSameSizeAs(dataResult[0]);
	assertThat(qtyTestCase).isEqualTo(dataResult.length);

	final Predicate<Integer> qtProdRule = p -> p.intValue() >= qtyMinValue && p.intValue() <= qtyMaxValue;

	final Predicate<String> nameRule = p -> {

	    if (length(p) > nameMaxLength) {
		return false;
	    }

	    if (length(p) < nameMinLength) {
		return false;
	    }

	    if (nameLowerLetters && p.matches("^(?=.*[a-z]).+$") == false) {
		return false;
	    }

	    if (nameUpperLetters && p.matches("^(?=.*[A-Z]).+$") == false) {
		return false;
	    }

	    if (nameNumbers && p.matches("^(?=.*\\d).+$") == false) {
		return false;
	    }

	    return true;
	};

	// @0formatter:off
	final var dataExpected = new Predicate[][] { //
		// Name , Qty Product //
		{ nameRule, qtProdRule }, //
	};
	// @formatter:on

	for (var i = 0; i < dataResult.length; i++) {

	    final var line = dataResult[i];

	    for (var j = 0; j < line.length; j++) {

		final var rule = dataExpected[0][j];
		final var value = dataResult[0][j];

		assertThat(rule).accepts(value);
	    }
	}
    }
}
