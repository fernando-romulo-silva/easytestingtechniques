package org.allsetconfigtest.boundaryvalue;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.cartesianProduct;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.slf4j.LoggerFactory.getLogger;
import static org.allsetconfigtest.ParamSpecInterface.MSG_INVALID_PARAMETERS;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;

/**
 * @author Fernando Romulo da Silva
 */
public final class BoundaryValueTesting {

    private static final Logger LOGGER = getLogger(BoundaryValueTesting.class);

    /**
     * Default constructor.
     */
    private BoundaryValueTesting() {
	throw new Error("You cant instance that class!");
    }

    /**
     * @param parameters
     * @return
     */
    @SuppressWarnings("unused")
    public static Object[][] normalBoundaryValueTesting(final BoundaryValueParamSpecInterface<?>... parameters) {

	LOGGER.debug("normalBoundaryValueTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, MSG_INVALID_PARAMETERS);

	final var result = new LinkedHashSet<List<Object>>();

	final var used = new ArrayList<BoundaryValueParamSpecInterface<?>>();

	for (final var parameterM : parameters) {

	    final var list = new ArrayList<List<Object>>();

	    var qty = 0;

	    for (final var parameterI : parameters) {

		if (qty == 0 && used.contains(parameterI) == false) {

		    list.add(new ArrayList<>(parameterI.getValidValues()));
		    used.add(parameterI);
		    qty++;

		} else {

		    list.add(List.of(parameterI.getNominalValue()));
		}
	    }

	    result.addAll(cartesianProduct(list));
	}

	LOGGER.debug("normalBoundaryValueTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }

    /**
     * @param parameters
     * @return
     */
    @SuppressWarnings("unused")
    public static Object[][] worstNormalBoundaryValueTesting(final BoundaryValueParamSpecInterface<?>... parameters) {

	LOGGER.debug("worstNormalBoundaryValueTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, MSG_INVALID_PARAMETERS);

	final var result = new LinkedHashSet<List<Object>>();

	for (final var parameterM : parameters) {

	    final var list = new ArrayList<List<Object>>();

	    for (final var parameterI : parameters) {

		list.add(new ArrayList<>(parameterI.getValidValues()));
	    }

	    result.addAll(cartesianProduct(list));
	}

	LOGGER.debug("worstNormalBoundaryValueTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }

    /**
     * @param parameters
     * @return
     */
    @SuppressWarnings("unused")
    public static Object[][] robustBoundaryValueTesting(final BoundaryValueParamSpecInterface<?>... parameters) {

	LOGGER.debug("robustBoundaryValueTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, MSG_INVALID_PARAMETERS);

	final var result = new LinkedHashSet<List<Object>>();

	final var used = new ArrayList<BoundaryValueParamSpecInterface<?>>();

	for (final var parameterM : parameters) {

	    final var list = new ArrayList<List<Object>>();

	    var qty = 0;

	    for (final var parameterI : parameters) {

		if (qty == 0 && used.contains(parameterI) == false) {

		    list.add(new ArrayList<>(parameterI.getValues()));
		    used.add(parameterI);
		    qty++;

		} else {

		    list.add(List.of(parameterI.getNominalValue()));
		}
	    }

	    result.addAll(cartesianProduct(list));
	}

	LOGGER.debug("robustBoundaryValueTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }

    /**
     * @param parameters
     * @return
     */
    @SuppressWarnings("unused")
    public static Object[][] worstRobustBoundaryValueTesting(final BoundaryValueParamSpecInterface<?>... parameters) {

	LOGGER.debug("worstRobustBoundaryValueTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, MSG_INVALID_PARAMETERS);

	final var result = new LinkedHashSet<List<Object>>();

	for (final var parameterM : parameters) {

	    final var list = new ArrayList<List<Object>>();

	    for (final var parameterI : parameters) {

		list.add(new ArrayList<>(parameterI.getValues()));
	    }

	    result.addAll(cartesianProduct(list));
	}

	LOGGER.debug("worstRobustBoundaryValueTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }

}
