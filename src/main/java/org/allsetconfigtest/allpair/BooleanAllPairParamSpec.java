package org.allsetconfigtest.allpair;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Fernando Romulo da Silva
 */
public final class BooleanAllPairParamSpec extends AbstractAllPairParamSpec<Boolean, BooleanAllPairParamSpec> implements AllPairParamSpecInterface<Boolean> {

    // ----------------------------------------------------------------------------------------------------------------------------------------

    public static BooleanAllPairParamSpec ofBoolean() {

	final var parameter = new BooleanAllPairParamSpec();
	parameter.addValues(TRUE, FALSE);

	return parameter;
    }

    public static BooleanAllPairParamSpec ofBooleanNullable() {

	final var parameter = new BooleanAllPairParamSpec();
	parameter.addValues(TRUE, FALSE, null);

	return parameter;
    }

    public static BooleanAllPairParamSpec ofBoolean(final String name) {

	final var parameter = new BooleanAllPairParamSpec(name);
	parameter.addValues(TRUE, FALSE);

	return parameter;
    }

    public static BooleanAllPairParamSpec ofBooleanNullable(final String name) {

	final var parameter = new BooleanAllPairParamSpec(name);
	parameter.addValues(TRUE, FALSE, null);

	return parameter;
    }

    // ----------------------------------------------------------------------------------------------------------------------------------------

    private BooleanAllPairParamSpec(final String name) {
	super(name);
    }

    private BooleanAllPairParamSpec() {
	super();
    }
}
