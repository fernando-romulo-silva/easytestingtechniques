package org.allsetconfigtest.combinatorial;

import org.allsetconfigtest.ValuesSupplier;

/**
 * Class that represent a parameter of method test.
 * 
 * Parameters refers to the list of variables in a method declaration. </br>
 * Arguments are the actual values that are passed in when the method is invoked. </br>
 * When you invoke a method, the arguments used must match the declaration's parameters in type and order. </br>
 * 
 * @author Fernando Romulo da Silva
 * 
 * @param <T>
 */
public final class ObjectCombinatorialParamSpec<T> extends AbstractCombinatorialParamSpec<T, ObjectCombinatorialParamSpec<T>> implements CombinatorialParamSpecInterface<T> {

    public static class ToType {

	public static <T> ObjectCombinatorialParamSpec<T> ofObject() {
	    return new ObjectCombinatorialParamSpec<T>();
	}

	public static <T> ObjectCombinatorialParamSpec<T> ofObject(final String name) {
	    return new ObjectCombinatorialParamSpec<T>(name);
	}
    }

    /**
     * Default constructor
     * 
     * @param name
     */
    private ObjectCombinatorialParamSpec(final String name) {
	super(name);
    }

    /**
     * Default constructor
     */
    private ObjectCombinatorialParamSpec() {
	super();
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public ObjectCombinatorialParamSpec<T> addValidValue(final T value) {
	return super.addValidValue(value);
    }

    @Override
    @SafeVarargs
    public final ObjectCombinatorialParamSpec<T> addValidValues(final T... values) {
	return super.addValidValues(values);
    }

    @Override
    public ObjectCombinatorialParamSpec<T> addValidValues(final ValuesSupplier<T> valuesSupplier) {
	return super.addValidValues(valuesSupplier);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public ObjectCombinatorialParamSpec<T> addInvalidValue(final T value) {
	return super.addInvalidValue(value);
    }

    @Override
    @SafeVarargs
    public final ObjectCombinatorialParamSpec<T> addInvalidValues(final T... values) {
	return super.addInvalidValues(values);
    }

    @Override
    public ObjectCombinatorialParamSpec<T> addInvalidValues(final ValuesSupplier<T> valuesSupplier) {
	return super.addInvalidValues(valuesSupplier);
    }
}
