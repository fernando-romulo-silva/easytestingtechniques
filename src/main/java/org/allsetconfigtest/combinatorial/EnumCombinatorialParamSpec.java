package org.allsetconfigtest.combinatorial;

public final class EnumCombinatorialParamSpec<T> extends AbstractCombinatorialParamSpec<Enum<? super T>, EnumCombinatorialParamSpec<T>> implements CombinatorialParamSpecInterface<Enum<? super T>> {

    public static class ToEnum {

	public static <T> EnumCombinatorialParamSpec<T> ofEnum() {
	    return new EnumCombinatorialParamSpec<T>();
	}

	public static <T> EnumCombinatorialParamSpec<T> ofEnum(final String name) {
	    return new EnumCombinatorialParamSpec<T>(name);
	}

	public static <T> EnumCombinatorialParamSpec<T> ofEnum(final Class<? extends Enum<? super T>> validValues) {

	    final var possibleValidArguments = validValues.getEnumConstants();
	    final var parameter = new EnumCombinatorialParamSpec<T>();
	    parameter.addValidValues(possibleValidArguments);

	    parameter.addInvalidValue(null);

	    return parameter;
	}

	public static <T> EnumCombinatorialParamSpec<T> ofEnum(final String name, final Class<? extends Enum<? super T>> validValues) {

	    final var possibleValidArguments = validValues.getEnumConstants();
	    final var parameter = new EnumCombinatorialParamSpec<T>(name);
	    parameter.addValidValues(possibleValidArguments);

	    parameter.addInvalidValue(null);

	    return parameter;
	}
    }

    /**
     * Default constructor
     * 
     * @param name
     */
    private EnumCombinatorialParamSpec(final String name) {
	super(name);
    }

    /**
     * Default constructor
     */
    private EnumCombinatorialParamSpec() {
	super();
    }

    /**
     * @param value
     * @return
     */
    public EnumCombinatorialParamSpec<T> addValidValue(final Enum<? super T> value) {
	return super.addValidValue(value);
    }

    /**
     * @param values
     * @return
     */
    @SafeVarargs
    public final EnumCombinatorialParamSpec<T> addValidValues(final Enum<? super T>... values) {
	return super.addValidValues(values);
    }

    /**
     * @param value
     * @return
     */
    public EnumCombinatorialParamSpec<T> addInvalidValue(final Enum<? super T> value) {
	return super.addInvalidValue(value);
    }

    /**
     * @param values
     * @return
     */
    @SafeVarargs
    public final EnumCombinatorialParamSpec<T> addInvalidValues(final Enum<? super T>... values) {
	return super.addInvalidValues(values);
    }
}
