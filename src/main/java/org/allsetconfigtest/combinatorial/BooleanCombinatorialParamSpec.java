package org.allsetconfigtest.combinatorial;

public final class BooleanCombinatorialParamSpec extends AbstractCombinatorialParamSpec<Boolean, BooleanCombinatorialParamSpec> implements CombinatorialParamSpecInterface<Boolean> {

    public static BooleanCombinatorialParamSpec ofBoolean() {
	return new BooleanCombinatorialParamSpec();
    }

    public static BooleanCombinatorialParamSpec ofBoolean(final String name) {
	return new BooleanCombinatorialParamSpec(name);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------

    private BooleanCombinatorialParamSpec(final String name) {
	super(name);
    }

    private BooleanCombinatorialParamSpec() {
	super();
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public BooleanCombinatorialParamSpec addValidValue(final Boolean value) {
	return super.addValidValue(value);
    }

    @Override
    public BooleanCombinatorialParamSpec addInvalidValue(final Boolean value) {
	return super.addInvalidValue(value);
    }

}
