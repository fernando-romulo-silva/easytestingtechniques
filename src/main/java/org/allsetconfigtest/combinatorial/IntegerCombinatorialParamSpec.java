package org.allsetconfigtest.combinatorial;

public final class IntegerCombinatorialParamSpec extends AbstractCombinatorialParamSpec<Integer, IntegerCombinatorialParamSpec> implements CombinatorialParamSpecInterface<Integer> {

    public static IntegerCombinatorialParamSpec ofInteger() {
	return new IntegerCombinatorialParamSpec();
    }

    public static IntegerCombinatorialParamSpec ofInteger(final String name) {
	return new IntegerCombinatorialParamSpec(name);
    }

    private IntegerCombinatorialParamSpec(final String name) {
	super(name);
    }

    private IntegerCombinatorialParamSpec() {
	super();
    }
}
