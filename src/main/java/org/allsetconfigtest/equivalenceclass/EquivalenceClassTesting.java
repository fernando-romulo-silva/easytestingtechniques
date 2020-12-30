package org.allsetconfigtest.equivalenceclass;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.cartesianProduct;
import static java.util.Collections.nCopies;
import static java.util.stream.Collectors.toList;
import static org.allsetconfigtest.ParamSpecInterface.MSG_INVALID_PARAMETERS;
import static org.allsetconfigtest.equivalenceclass.EquivanceClassType.INVALID;
import static org.allsetconfigtest.equivalenceclass.EquivanceClassType.VALID;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;

/**
 * @author fernando
 *
 */
public final class EquivalenceClassTesting {

    private static final Logger LOGGER = getLogger(EquivalenceClassTesting.class);

    /**
     * Default constructor.
     */
    private EquivalenceClassTesting() {
	throw new Error("You cant instance that class!");
    }

    private static <T> T randomValueFromClasses(final Set<EquivalenceClass<T>> classes) {

	final var rand = new Random();

	final var valuesList = new ArrayList<>(classes);

	final var selectedClass = valuesList.get(rand.nextInt(classes.size()));

	return selectedClass.getRandomValue();
    }

    /**
     * @param parameters
     * @return
     */
    public static Object[][] traditionalEquivalenceClassTesting(final EquivalenceClassParamSpecInterface<?>... parameters) {

	LOGGER.debug("traditionalEquivalenceClassTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, MSG_INVALID_PARAMETERS);

	final var result = new LinkedHashSet<List<Object>>();

	final var validValues = new ArrayList<>();

	for (final var parameter : parameters) {
	    validValues.add(randomValueFromClasses(parameter.getValidClasses()));
	}

	result.add(validValues);

	for (final var parameterM : parameters) {

	    final var list = new ArrayList<List<Object>>();

	    for (final var parameterI : parameters) {

		if (Objects.equals(parameterM, parameterI)) {

		    final var invalidValues = parameterM.getInvalidClasses() //
			    .stream() //
			    .map(c -> c.getRandomValue()) //
			    .collect(toList());

		    list.add(new ArrayList<>(invalidValues));

		} else {

		    list.add(List.of(randomValueFromClasses(parameterI.getValidClasses())));
		}
	    }

	    result.addAll(cartesianProduct(list));
	}

	LOGGER.debug("normalBoundaryValueTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }

    public static Object[][] weakNormalEquivalenceClassTesting(final EquivalenceClassParamSpecInterface<?>... parameters) {

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, MSG_INVALID_PARAMETERS);

	final var result = weakEquivalenceClassTesting(VALID, parameters);

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }

    @SuppressWarnings("unused")
    public static Object[][] strongNormalEquivalenceClassTesting(final EquivalenceClassParamSpecInterface<?>... parameters) {

	LOGGER.debug("strongNormalEquivalenceClassTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, MSG_INVALID_PARAMETERS);

	final var result = new LinkedHashSet<List<Object>>();

	for (final var parameterM : parameters) {

	    final var list = new ArrayList<List<Object>>();

	    for (final var parameterI : parameters) {

		list.add( //
			parameterI.getValidClasses() //
				.stream() //
				.map(c -> c.getDefaultValue()) //
				.collect(toList()) //
		);
	    }

	    result.addAll(cartesianProduct(list));
	}

	LOGGER.debug("strongNormalEquivalenceClassTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }

    public static Object[][] weakRobustEquivalenceClassTesting(final EquivalenceClassParamSpecInterface<?>... parameters) {

	LOGGER.debug("weakRobustEquivalenceClassTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, MSG_INVALID_PARAMETERS);

	final var result = new LinkedHashSet<List<Object>>();

	result.addAll(weakEquivalenceClassTesting(INVALID, parameters));

	result.addAll(weakEquivalenceClassTesting(VALID, parameters));

	LOGGER.debug("weakRobustEquivalenceClassTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }

    /**
     * @param result
     * @param parameters
     */
    private static LinkedHashSet<List<Object>> weakEquivalenceClassTesting(final EquivanceClassType type, final EquivalenceClassParamSpecInterface<?>... parameters) {

	final var result = new LinkedHashSet<List<Object>>();

	final var max = Stream.of(parameters) //
		.map(p -> type == VALID ? p.getValidClasses() : p.getInvalidClasses()) //
		.mapToInt(p -> p.size()) //
		.max().getAsInt();

	final var values = new ArrayList<List<?>>();

	for (final var parameter : parameters) {

	    final var valuesTemp = parameter.getClasses() //
		    .stream() //
		    .filter(c -> (c.isValid() && type == VALID) || (c.isInvalid() && type == INVALID)).map(c -> c.getRandomValue()) //
		    .collect(toList());

	    final var valuesTempFinal = nCopies(max, valuesTemp) //
		    .stream() //
		    .flatMap(n -> n.stream()) //
		    .limit(max) //
		    .collect(toList());

	    values.add(valuesTempFinal);
	}

	for (int i = 0; i < max; i++) {

	    final var c = new ArrayList<Object>();

	    for (final var list : values) {
		c.add(list.get(i));
	    }

	    result.add(c);
	}

	return result;
    }

    @SuppressWarnings("unused")
    public static Object[][] strongRobustEquivalenceClassTesting(final EquivalenceClassParamSpecInterface<?>... parameters) {

	LOGGER.debug("strongRobustEquivalenceClassTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, MSG_INVALID_PARAMETERS);

	final var result = new LinkedHashSet<List<Object>>();

	for (final var parameterM : parameters) {

	    final var list = new ArrayList<List<Object>>();

	    for (final var parameterI : parameters) {

		list.add( //
			parameterI.getClassesNaturalOrder() //
				.stream() //
				.map(c -> c.getDefaultValue()) //
				.collect(toList()) //
		);
	    }

	    result.addAll(cartesianProduct(list));
	}

	LOGGER.debug("strongRobustEquivalenceClassTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }
}
