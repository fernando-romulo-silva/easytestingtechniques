package org.allsetconfigtest.equivalenceclass;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

import java.util.stream.Stream;

public final class IntegerEquivalenceClassParamSpec extends AbstractEquivalenceClassParamSpec<Integer, IntegerEquivalenceClassParamSpec> implements EquivalenceClassParamSpecInterface<Integer> {

    protected final SpecRange<Integer> specRange;

    public static IntegerEquivalenceClassParamSpec ofInteger(final SpecRange<Integer> parameterRange) {
	return new IntegerEquivalenceClassParamSpec(parameterRange);
    }

    public static IntegerEquivalenceClassParamSpec ofInteger(final String name, final SpecRange<Integer> parameterRange) {
	return new IntegerEquivalenceClassParamSpec(name, parameterRange);
    }

    /**
     * Default constructor
     */
    private IntegerEquivalenceClassParamSpec(final SpecRange<Integer> newParameterRange) {
	super();
	checkArgument(nonNull(newParameterRange), "Parameter 'parameterRange' is invalid!");
	this.specRange = newParameterRange;
    }

    /**
     * Default constructor
     * 
     * @param name The name of parameter
     */
    private IntegerEquivalenceClassParamSpec(final String name, final SpecRange<Integer> newParameterRange) {
	super(name);
	checkArgument(nonNull(newParameterRange), "Parameter 'parameterRange' is invalid!");
	this.specRange = newParameterRange;
    }

    @Override
    protected void generateValues(final EquivalenceClass<Integer> clazz) {

	final var minValue = specRange.getMinValue();
	final var increment = specRange.getIncrement();
	final var maxValue = specRange.getMaxValue();

	final var values = Stream //
		.iterate(minValue, i -> i + increment) //
		.parallel() //
		.limit(maxValue) //
		.filter(clazz.getRule()) //
		.collect(toSet());

	clazz.loadValues(values);
    }
}
