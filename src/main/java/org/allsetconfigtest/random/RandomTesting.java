package org.allsetconfigtest.random;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.nCopies;
import static java.util.stream.Collectors.toList;
import static org.allsetconfigtest.ParamSpecInterface.MSG_INVALID_PARAMETERS;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;

public class RandomTesting {

    private static final Logger LOGGER = getLogger(RandomTesting.class);

    /**
     * Default constructor.
     */
    private RandomTesting() {
	throw new Error("You cant instance that class!");
    }

    /**
     * @param parameters
     * @return
     */
    public static Object[][] randomTesting(final RandomParamSpecInterface<?>... parameters) {

	LOGGER.debug("randomTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, MSG_INVALID_PARAMETERS);

	final var result = new LinkedHashSet<List<Object>>();

	final var max = Stream.of(parameters) //
		.mapToInt(p -> p.getValues().size()) //
		.max().getAsInt();

	final var values = new ArrayList<List<?>>();

	for (final var parameter : parameters) {

	    final var valuesTemp = parameter.getValues() //
		    .stream() //
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

	LOGGER.debug("randomTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }
}
