package org.allsetconfigtest.equivalenceclass;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toCollection;

import java.util.LinkedHashSet;
import java.util.Set;

import org.allsetconfigtest.ParamSpecInterface;

public interface EquivalenceClassParamSpecInterface<T> extends ParamSpecInterface<T> {

    /**
     * Return all parameter's values
     * 
     * @return a <code>Set</code> of <T>
     */
    Set<EquivalenceClass<T>> getClasses();

    /**
     * @return
     */
    default Set<EquivalenceClass<T>> getClassesNaturalOrder() {

	final var validClasses = getValidClasses();
	final var invalidClasses = getInvalidClasses();

	final var result = new LinkedHashSet<EquivalenceClass<T>>();

	result.addAll(invalidClasses);
	result.addAll(validClasses);

	return unmodifiableSet(result);
    }

    /**
     * @return
     */
    default Set<EquivalenceClass<T>> getValidClasses() {

	final Set<EquivalenceClass<T>> t = getClasses().stream() //
		.filter(c -> c.isValid()) //
		.collect(toCollection(LinkedHashSet::new));

	return unmodifiableSet(t);
    }

    /**
     * @return
     */
    default Set<EquivalenceClass<T>> getInvalidClasses() {

	final Set<EquivalenceClass<T>> t = getClasses().stream() //
		.filter(c -> c.isInvalid()) //
		.collect(toCollection(LinkedHashSet::new));

	return unmodifiableSet(t);
    }
}
