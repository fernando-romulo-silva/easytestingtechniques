package org.allsetconfigtest.combinatorial;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

import java.util.LinkedHashSet;
import java.util.Set;

import org.allsetconfigtest.AbstractParamSpec;
import org.allsetconfigtest.ValuesSupplier;

/**
 * @author Fernando Romulo da Silva
 *
 * @param <T>
 */
abstract class AbstractCombinatorialParamSpec<T, R extends AbstractCombinatorialParamSpec<T, R>> extends AbstractParamSpec<T, R> implements CombinatorialParamSpecInterface<T> {

    protected final Set<T> validValues = new LinkedHashSet<>();

    protected final Set<T> invalidValues = new LinkedHashSet<>();

    /**
     * Default constructor
     */
    protected AbstractCombinatorialParamSpec() {
	super();
    }

    /**
     * Default constructor
     * 
     * @param name
     */
    protected AbstractCombinatorialParamSpec(final String name) {
	super(name);
    }

    /**
     * @param value
     * @return
     */
    protected R addValidValue(final T value) {

	checkArgument(nonNull(value), "Parameter 'value' is invalid!");

	this.validValues.add(value);

	return getThis();
    }

    /**
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    protected R addValidValues(final T... values) {

	checkArgument(isNotEmpty(values), "Parameter 'values' is invalid!");

	this.validValues.addAll(asList(values));

	return getThis();
    }

    /**
     * @param Valuesupplier
     * @return
     */
    protected R addValidValues(final ValuesSupplier<T> valuesSupplier) {

	checkArgument(nonNull(valuesSupplier), "Parameter 'valuesSupplier' is invalid!");

	this.validValues.addAll(valuesSupplier.get());

	return getThis();
    }

    // ----------------------------------------------------------------------------------------------------------

    /**
     * @param value
     * @return
     */
    protected R addInvalidValue(final T value) {
	this.invalidValues.add(value);
	return getThis();
    }

    /**
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    protected R addInvalidValues(final T... values) {

	checkArgument(isNotEmpty(values), "Parameter 'values' is invalid!");

	this.invalidValues.addAll(asList(values));

	return getThis();
    }

    /**
     * @param Valuesupplier
     * @return
     */
    protected R addInvalidValues(final ValuesSupplier<T> valuesSupplier) {

	checkArgument(nonNull(valuesSupplier), "Parameter 'valuesSupplier' is invalid!");

	this.invalidValues.addAll(valuesSupplier.get());

	return getThis();
    }

    /**
     * @return
     */
    public T getDefaultValidValue() {
	return this.validValues.iterator().next();
    }

    /**
     * @return
     */
    public T getDefaultInvalidValue() {
	return this.invalidValues.iterator().next();
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
