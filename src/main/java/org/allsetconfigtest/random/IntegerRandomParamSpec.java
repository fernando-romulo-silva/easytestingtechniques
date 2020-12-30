package org.allsetconfigtest.random;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.RandomUtils.nextInt;

/**
 * @author Fernando Romulo da Silva
 */
public final class IntegerRandomParamSpec extends AbstractRandomParamSpec<Integer, IntegerRandomParamSpec> {

    private final int minValue;

    private final int maxValue;

    // --------------------------------------------------------------------------------------------------------------

    public static IntegerRandomParamSpec ofInteger() {
	return new IntegerRandomParamSpec();
    }

    public static IntegerRandomParamSpec ofInteger(final String name, final int minValue, final int maxValue) {
	return new IntegerRandomParamSpec(name, minValue, maxValue);
    }

    public static IntegerRandomParamSpec ofInteger(final String name, final int qtyValues, final int minValue, final int maxValue) {
	return new IntegerRandomParamSpec(name, qtyValues, minValue, maxValue);
    }

    // --------------------------------------------------------------------------------------------------------------

    private IntegerRandomParamSpec() {
	super();
	this.minValue = MIN_VALUE;
	this.maxValue = MAX_VALUE;
    }

    private IntegerRandomParamSpec(final String name, final int minValue, final int maxValue) {
	super(name);
	this.minValue = minValue;
	this.maxValue = maxValue;
    }

    private IntegerRandomParamSpec(final String name, final int qtyValues, final int minValue, final int maxValue) {
	super(name, qtyValues);
	this.minValue = minValue;
	this.maxValue = maxValue;
    }

    // --------------------------------------------------------------------------------------------------------------

    @Override
    protected void generateValues() {

	if (isNotEmpty(values)) {
	    return;
	}

	var i = 1;

	var done = false;

	while (done == false) {
	    
	    if (i >= qtyValues * 3) {
		done = true;
	    } else if (values.size() >= qtyValues) {
		done = true;
	    } else {

		var randomValue = 0;

		if (minValue < 0 && maxValue > 0) {

		    randomValue = nextInt(0, maxValue - minValue) + minValue;

		} else if (minValue < 0 && maxValue <= 0) {

		    randomValue = nextInt(maxValue * -1, minValue * -1) * -1;

		} else {

		    randomValue = nextInt(minValue, maxValue);
		}

		values.add(randomValue);
	    }

	    i++;
	}
    }
}
