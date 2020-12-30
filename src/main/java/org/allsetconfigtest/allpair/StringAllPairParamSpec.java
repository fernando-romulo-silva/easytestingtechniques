package org.allsetconfigtest.allpair;

import org.allsetconfigtest.ValuesSupplier;

/**
 * 
 * @author Fernando Romulo da Silva
 */
public class StringAllPairParamSpec extends AbstractAllPairParamSpec<String, StringAllPairParamSpec> implements AllPairParamSpecInterface<String> {

    public static StringAllPairParamSpec ofString() {
	return new StringAllPairParamSpec();
    }

    public static StringAllPairParamSpec ofString(final String name) {
	return new StringAllPairParamSpec(name);
    }

    /**
     * Default constructor
     * 
     * @param name
     */
    private StringAllPairParamSpec(final String name) {
	super(name);
    }

    /**
     * Default constructor
     */
    private StringAllPairParamSpec() {
	super();
    }

    /**
    *
    */
    @Override
    public StringAllPairParamSpec addValue(final String value) {
	return super.addValue(value);
    }

    /**
     *
     */
    @Override
    @SafeVarargs
    public final StringAllPairParamSpec addValues(final String... value) {
	return super.addValues(value);
    }

    /**
     *
     */
    @Override
    public StringAllPairParamSpec addValues(final ValuesSupplier<String> valuesSupplier) {
	return super.addValues(valuesSupplier);
    }
}
