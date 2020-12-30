package org.allsetconfigtest.allpair;

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
public final class ObjectAllPairParamSpec<T> extends AbstractAllPairParamSpec<T, ObjectAllPairParamSpec<T>> implements AllPairParamSpecInterface<T> {

    public static class ToType {

	public static <T> ObjectAllPairParamSpec<T> ofObject() {
	    return new ObjectAllPairParamSpec<T>();
	}

	public static <T> ObjectAllPairParamSpec<T> ofObject(final String name) {
	    return new ObjectAllPairParamSpec<T>(name);
	}
    }

    /**
     * Default constructor
     * 
     * @param name
     */
    private ObjectAllPairParamSpec(final String name) {
	super(name);
    }

    /**
     * Default constructor
     */
    private ObjectAllPairParamSpec() {
	super();
    }

    /**
    *
    */
    @Override
    public ObjectAllPairParamSpec<T> addValue(final T value) {
	return super.addValue(value);
    }

    /**
     *
     */
    @Override
    @SafeVarargs
    public final ObjectAllPairParamSpec<T> addValues(final T... value) {
	return super.addValues(value);
    }

    /**
     *
     */
    @Override
    public ObjectAllPairParamSpec<T> addValues(final ValuesSupplier<T> valuesSupplier) {
	return super.addValues(valuesSupplier);
    }
}
