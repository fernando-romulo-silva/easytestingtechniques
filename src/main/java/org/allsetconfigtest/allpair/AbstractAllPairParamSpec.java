package org.allsetconfigtest.allpair;

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
 * @param <R>
 */
public abstract class AbstractAllPairParamSpec<T, R extends AbstractAllPairParamSpec<T, R>> extends AbstractParamSpec<T, R> implements AllPairParamSpecInterface<T> {

    protected final Set<T> values = new LinkedHashSet<>();

    /**
     * Default constructor
     */
    protected AbstractAllPairParamSpec() {
	super();
    }

    /**
     * Default constructor
     * 
     * @param name
     */
    protected AbstractAllPairParamSpec(final String name) {
	super(name);
    }

    @Override
    public Set<T> getValues() {
	return unmodifiableSet(values);
    }

    /**
     * @param value
     * @return
     */
    protected R addValue(final T value) {

	this.values.add(value);

	return getThis();
    }

    /**
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    protected R addValues(final T... values) {

	checkArgument(isNotEmpty(values), "Parameter 'values' is invalid!");

	this.values.addAll(asList(values));

	return getThis();
    }

    /**
     * @param Valuesupplier
     * @return
     */
    protected R addValues(final ValuesSupplier<T> valuesSupplier) {

	checkArgument(nonNull(valuesSupplier), "Parameter 'valuesSupplier' is invalid!");

	this.values.addAll(valuesSupplier.get());

	return getThis();
    }
}
