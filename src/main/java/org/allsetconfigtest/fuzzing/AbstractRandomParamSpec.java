package org.allsetconfigtest.fuzzing;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.unmodifiableSet;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.allsetconfigtest.AbstractParamSpec;

/**
 * @author Fernando Romulo da Silva
 *
 * @param <T>
 * @param <R>
 */
abstract class AbstractRandomParamSpec<T, R extends AbstractParamSpec<T, R>> extends AbstractParamSpec<T, R> implements FuzzingParamSpecInterface<T> {

    protected final Set<T> seeds = new HashSet<>();

    protected final Set<T> values = new LinkedHashSet<>();

    protected final int qtyValues;

    /**
     * Default constructor
     */
    protected AbstractRandomParamSpec() {
	super();
	qtyValues = 10;
    }

    /**
     * Default constructor
     * 
     * @param name
     * @param clazzType
     */
    protected AbstractRandomParamSpec(final String name) {
	super(name);
	this.qtyValues = 10;
    }

    /**
     * Default constructor
     * 
     * @param name
     * @param clazzType
     * @param qtyValues
     */
    protected AbstractRandomParamSpec(final String name, final int qtyValues) {
	super(name);

	checkArgument(qtyValues > 1, "Parameter 'qtyValues' can't be less than zero!");

	this.qtyValues = qtyValues;
    }

    /**
     *
     */
    @Override
    public Set<T> getValues() {

	if (isEmpty(values)) {
	    generateValues();
	}

	return unmodifiableSet(values);
    }

    /**
     * 
     */
    protected abstract void generateValues();
}
