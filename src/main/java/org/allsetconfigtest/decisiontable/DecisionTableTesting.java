package org.allsetconfigtest.decisiontable;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;

public class DecisionTableTesting {

    private static final Logger LOGGER = getLogger(DecisionTableTesting.class);

    /**
     * Default constructor.
     */
    private DecisionTableTesting() {
	throw new Error("You cant instance that class!");
    }

    /**
     * @param parameters
     * @return
     */
    public static Object[][] decisionTableTesting(final DecisionTableParameterInterface<?>... parameters) {

	LOGGER.debug("smartFuzzingTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, "Argument 'parameters' is invalid!");

	final var result = new LinkedHashSet<List<Object>>();

	LOGGER.debug("smartFuzzingTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }
}
