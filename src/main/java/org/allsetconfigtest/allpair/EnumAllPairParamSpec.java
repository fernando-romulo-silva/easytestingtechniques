package org.allsetconfigtest.allpair;

import org.allsetconfigtest.ValuesSupplier;

/**
 * @author Fernando Romulo da Silva
 *
 * @param <T>
 */
public final class EnumAllPairParamSpec<T> extends AbstractAllPairParamSpec<Enum<? super T>, EnumAllPairParamSpec<T>> implements AllPairParamSpecInterface<Enum<? super T>> {

    public static class ToEnum {

	public static <T> EnumAllPairParamSpec<T> ofEnum() {
	    return new EnumAllPairParamSpec<T>();
	}

	public static <T> EnumAllPairParamSpec<T> ofEnum(final String name) {
	    return new EnumAllPairParamSpec<T>(name);
	}

	public static <T> EnumAllPairParamSpec<T> ofEnum(final String name, final Class<? extends Enum<? super T>> values) {

	    final var possibleValidArguments = values.getEnumConstants();
	    final var parameter = new EnumAllPairParamSpec<T>(name);
	    parameter.addValues(possibleValidArguments);

	    return parameter;
	}

	public static <T> EnumAllPairParamSpec<T> ofEnum(final Class<? extends Enum<? super T>> values) {

	    final var possibleValidArguments = values.getEnumConstants();
	    final var parameter = new EnumAllPairParamSpec<T>();
	    parameter.addValues(possibleValidArguments);

	    return parameter;
	}
    }

    /**
     * Default constructor
     * 
     * @param name
     */
    private EnumAllPairParamSpec(final String name) {
	super(name);
    }

    /**
     * Default constructor
     */
    private EnumAllPairParamSpec() {
	super();
    }

    /**
    *
    */
    @Override
    public EnumAllPairParamSpec<T> addValue(final Enum<? super T> value) {
	return super.addValue(value);
    }

    /**
     *
     */
    @Override
    @SafeVarargs
    public final EnumAllPairParamSpec<T> addValues(final Enum<? super T>... values) {
	return super.addValues(values);
    }

    /**
     *
     */
    @Override
    public EnumAllPairParamSpec<T> addValues(final ValuesSupplier<Enum<? super T>> valuesSupplier) {
	return super.addValues(valuesSupplier);
    }
}
