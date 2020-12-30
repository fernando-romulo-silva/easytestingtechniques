package org.allsetconfigtest.boundaryvalue;

import org.apache.commons.lang3.Range;

/**
 * @author Fernando Romulo da Silva
 */
public final class IntegerBoundaryParamSpec extends AbstractBoundaryValueParamSpec<Integer, IntegerBoundaryParamSpec> implements BoundaryValueParamSpecInterface<Integer> {

    // ----------------------------------------------------------------------------------------------------------------------------------------

    public static IntegerBoundaryParamSpec ofInteger(final Integer nominal, final Integer begin, final Integer end) {
	final var integerParameter = new IntegerBoundaryParamSpec();
	return integerParameter.addValues(nominal, begin, end);
    }

    public static IntegerBoundaryParamSpec ofInteger(final String name, final Integer nominal, final Integer begin, final Integer end) {
	final var integerParameter = new IntegerBoundaryParamSpec(name);
	return integerParameter.addValues(nominal, begin, end);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------

    private IntegerBoundaryParamSpec() {
	super();
    }

    private IntegerBoundaryParamSpec(final String name) {
	super(name);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected IntegerBoundaryParamSpec addValues(final Integer nominal, final Integer begin, final Integer end) {

	final var range = Range.between(begin + 1, end - 1);

	if (range.contains(nominal) == false) {
	    // wrong nominal
	}

	validValues.add(nominal); // first nominal

	validValues.add(begin);
	validValues.add(begin + 1);

	validValues.add(end - 1);
	validValues.add(end);

	invalidValues.add(begin.intValue() - 1);
	invalidValues.add(end.intValue() + 1);

	return getThis();
    }
}
