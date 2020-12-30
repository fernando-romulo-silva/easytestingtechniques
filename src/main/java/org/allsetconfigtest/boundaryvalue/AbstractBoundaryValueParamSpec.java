package org.allsetconfigtest.boundaryvalue;

import static java.util.Collections.unmodifiableSet;

import java.util.LinkedHashSet;
import java.util.Set;

import org.allsetconfigtest.AbstractParamSpec;

/**
 * @author Fernando Romulo da Silva
 *
 * @param <T>
 */
abstract class AbstractBoundaryValueParamSpec<T extends Comparable<T>, R extends AbstractBoundaryValueParamSpec<T, R>> extends AbstractParamSpec<T, R> implements BoundaryValueParamSpecInterface<T> {

    protected final Set<T> validValues = new LinkedHashSet<>();

    protected final Set<T> invalidValues = new LinkedHashSet<>();

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     */
    protected AbstractBoundaryValueParamSpec() {
	super();
    }

    /**
     * Default constructor
     * 
     * @param name
     */
    protected AbstractBoundaryValueParamSpec(final String name) {
	super(name);
    }

    /**
     * @param nominal
     * @param begin
     * @param end
     * @return
     */
    protected abstract R addValues(final T nominal, final T begin, final T end);

    /*
     * (non-Javadoc)
     * 
     * @see org.allsetconfigtest.combinatorial.parameter.CombinatorialParameterInterface#getDefaultValue()
     */
    @Override
    public T getNominalValue() {

	final var iter = validValues.iterator();

	if (iter.hasNext()) {
	    return iter.next();
	}

	return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.allsetconfigtest.combinatorial.parameter.CombinatorialParameterInterface#getValues()
     */
    @Override
    public Set<T> getValues() {
	final var setTemp = new LinkedHashSet<>(validValues);
	setTemp.addAll(invalidValues);
	return unmodifiableSet(setTemp);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.allsetconfigtest.combinatorial.parameter.CombinatorialParameterInterface#getValidValues()
     */
    @Override
    public Set<T> getValidValues() {
	return unmodifiableSet(validValues);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.allsetconfigtest.combinatorial.parameter.CombinatorialParameterInterface#getInvalidValues()
     */
    @Override
    public Set<T> getInvalidValues() {
	return unmodifiableSet(invalidValues);
    }
}
