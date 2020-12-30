package org.allsetconfigtest.equivalenceclass;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.nonNull;
import static org.allsetconfigtest.equivalenceclass.EquivalenceClass.newInvalidClass;
import static org.allsetconfigtest.equivalenceclass.EquivalenceClass.newValidClass;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.allsetconfigtest.AbstractParamSpec;

/**
 * @author Fernando Romulo da Silva
 *
 * @param <T>
 * @param <R>
 */
abstract class AbstractEquivalenceClassParamSpec<T extends Number & Comparable<T>, R extends AbstractEquivalenceClassParamSpec<T, R>> extends AbstractParamSpec<T, R> implements EquivalenceClassParamSpecInterface<T> {

    protected final Set<EquivalenceClass<T>> classes = new LinkedHashSet<>();

    /**
     * Default constructor
     */
    protected AbstractEquivalenceClassParamSpec() {
	super();
    }

    /**
     * @param name
     */
    protected AbstractEquivalenceClassParamSpec(final String name) {
	super(name);
    }

    @Override
    public Set<EquivalenceClass<T>> getClasses() {
	return unmodifiableSet(classes);
    }

    @SafeVarargs
    public final R addValidClasses(final Predicate<T>... newRules) {

	checkArgument(isNotEmpty(newRules), "Parameter 'newRules' can't be empty!");

	for (final var rule : newRules) {
	    final var clazz = newValidClass(rule);
	    generateValues(clazz);
	    classes.add(clazz);
	}

	return getThis();
    }

    @SafeVarargs
    public final R addInvalidClasses(final Predicate<T>... newRules) {

	checkArgument(isNotEmpty(newRules), "Parameter 'newRules' can't be empty!");

	for (final var rule : newRules) {
	    final var clazz = newInvalidClass(rule);
	    generateValues(clazz);
	    classes.add(clazz);
	}

	return getThis();
    }

    public final R addValidClass(final String name, final Predicate<T> newRule) {

	checkArgument(nonNull(newRule), "Parameter 'newRule' can't be null!");

	final var clazz = newValidClass(name, newRule);
	generateValues(clazz);
	classes.add(clazz);

	return getThis();
    }

    public final R addInvalidClass(final String name, final Predicate<T> newRule) {

	checkArgument(nonNull(newRule), "Parameter 'newRule' can't be null!");

	final var clazz = newInvalidClass(name, newRule);
	generateValues(clazz);
	classes.add(clazz);

	return getThis();
    }

    @SafeVarargs
    public final R addClasses(final EquivalenceClass<T>... newClasses) {

	checkArgument(isNotEmpty(newClasses), "Parameter 'newClasses' can't be empty!");

	for (final var clazz : newClasses) {
	    generateValues(clazz);
	    classes.add(clazz);
	}

	return getThis();
    }

    /**
     *
     */
    @Override
    public final Set<T> getValues() {

	return null;
    }

    /**
     * @param clazz
     */
    protected abstract void generateValues(final EquivalenceClass<T> clazz);

}
