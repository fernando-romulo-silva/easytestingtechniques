package org.allsetconfigtest;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;
import static nl.jqno.equalsverifier.EqualsVerifier.forClass;
import static nl.jqno.equalsverifier.Warning.ALL_FIELDS_SHOULD_BE_USED;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static nl.jqno.equalsverifier.Warning.STRICT_HASHCODE;
import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;

/**
 * Util class to test trivial methods, like constructors, equals, hashcode, enums, etc.
 * 
 * @author Fernando Romulo da Silva
 */
public class TestingObject {

    private static final Logger LOGGER = getLogger(TestingObject.class);

    /**
     * Default constructor.
     */
    private TestingObject() {
	throw new Error("You cant instance that class!");
    }

    /**
     * Test utils classes. That classes can't instantiate.
     * 
     * @param <T>   The type of class
     * @param clazz The class
     * @throws InvocationTargetException if occur any error
     */
    public static <T> void constructorUtilClass(final Class<T> clazz) throws InvocationTargetException {

	LOGGER.debug("constructorUtilClass begins with clazz {}", clazz);

	checkArgument(clazz != null, "Argument 'clazz' can't be null!");

	try {
	    final var constructor = clazz.getDeclaredConstructor();
	    final var isPrivate = Modifier.isPrivate(constructor.getModifiers());

	    if (!isPrivate) {
		throw new IllegalStateException("There are public constructors.");
	    }

	    constructor.setAccessible(true);
	    constructor.newInstance();

	    LOGGER.debug("constructorUtilClass succeeds with clazz {}", clazz);

	} catch (final NoSuchMethodException | IllegalAccessException | InstantiationException ex) {

	    LOGGER.debug("constructorUtilClass fail with clazz {} ", clazz, ex);
	    throw new IllegalStateException(ex);
	}
    }

    /**
     * Test Equals and HashCode methods of the class.
     * 
     * @param <T>   The type of class
     * @param clazz The class
     */
    public static <T> void equalsAndHashCode(final Class<T> clazz) {

	LOGGER.debug("equalsAndHashCode begins with {}", clazz);

	checkArgument(nonNull(clazz), "Argument 'clazz' can't be null!");

	forClass(clazz).suppress(NULL_FIELDS, STRICT_HASHCODE, ALL_FIELDS_SHOULD_BE_USED, STRICT_INHERITANCE).verify();

	LOGGER.debug("equalsAndHashCode succeeds with {}", clazz);
    }

    /**
     * Test Equals and HashCode methods of the objects.
     * 
     * @param <T>       The type of class
     * @param object    An instance of <T>
     * @param same      An another instance of <T> equals to object
     * @param different An another instance of <T> not equals to object
     */
    public static <T> void equalsAndHashCode(final T object, T same, T different) {

	LOGGER.debug("equalsAndHashCode begins with object {}, same {}, different {}", object, same, different);

	checkArgument(object != null, "Argument 'object' can't be null!");

	checkArgument(same != null, "Argument 'same' can't be null!");

	checkArgument(different != null, "Argument 'different' can't be null!");

	final boolean equalsItself = object.equals(object);

	checkState(equalsItself, "The object not equals itself!");

	final boolean equalsSame = object.equals(same);

	checkState(equalsSame, "The object not equals the same!");

	final boolean equalsDifferent = !object.equals(different);

	checkState(equalsDifferent, "The object equals the different!");

	final boolean equalsNull = !object.equals(null);

	checkState(equalsNull, "The object equals the null!");

	final boolean equalsDifferentType = !object.equals(new IllegalStateException());

	checkState(equalsDifferentType, "The object equals the IllegalStateException!");

	final boolean hashcodeSame = (object.hashCode() == same.hashCode());

	checkState(hashcodeSame, "The hash code is different of the same!");

	final boolean hashcodeDifferent = object.hashCode() != different.hashCode();

	checkState(hashcodeDifferent, "The hash code is equal of the different!");

	LOGGER.debug("equalsAndHashCode succeeds with object {}, same {}, different {}", object, same, different);
    }

    /**
     * Test the default enums methods.
     * 
     * @param enumClass The enums
     */
    public static void enumItems(final Class<? extends Enum<?>> enumClass) {

	LOGGER.debug("enumItems test begins with enumClass {}", enumClass);

	checkArgument(enumClass != null, "Argument 'enumClass' is invalid!");

	try {
	    for (final var o : (Object[]) enumClass.getMethod("values").invoke(null)) {
		enumClass.getMethod("valueOf", String.class).invoke(null, o.toString());
	    }
	} catch (final ReflectiveOperationException ex) {

	    LOGGER.error("enumItems test fails with enumClass {}", enumClass, ex);

	    throw new IllegalStateException(ex);
	}

	LOGGER.debug("enumItems test succeeds with enumClass {}", enumClass);
    }

}
