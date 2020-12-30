package org.allsetconfigtest.allpair;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;
import static org.allsetconfigtest.ParamSpecInterface.MSG_INVALID_PARAMETERS;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;

import com.abslab.lib.pairwise.gen.PairwiseGenerator;

/**
 * This class create use all pair use tests cases
 * 
 * @author Fernando Romulo da Silva
 */
public final class AllPairTesting {

    private static final Logger LOGGER = getLogger(AllPairTesting.class);

    /**
     * Default constructor.
     */
    private AllPairTesting() {
	throw new Error("You cant instance that class!");
    }

    /**
     * @param parameters
     * @return
     */
    public static Object[][] allPairValues(final AllPairParamSpecInterface<?>... parameters) {

	LOGGER.debug("allPairWithValidValues begins with parameters {}", (Object[]) parameters);

	checkArgument(isEmpty(parameters) == false && contains(parameters, null) == false, MSG_INVALID_PARAMETERS);

	final var params = new LinkedHashMap<String, List<Object>>();

	for (final var parameter : parameters) {
	    params.put(parameter.getName(), new ArrayList<>(parameter.getValues()));
	}

	final var gen = new PairwiseGenerator<String, Object>(params);

	final var orderCases = gen.getGenaratedCases();

	final var keysPosReal = new ArrayList<>(params.keySet());
	final var keysPosAtual = new ArrayList<>(orderCases.keySet());

	final var posMap = new LinkedHashMap<Integer, Integer>();

	var posReal = 0;
	var posActual = 0;

	for (final var key : keysPosReal) {

	    posActual = keysPosAtual.indexOf(key);

	    posMap.put(posActual, posReal);

	    posReal++;
	}

	final var table = gen.stream().collect(toList());

	final var size = table.size();

	final var result = new Object[size][parameters.length];

	var i = 0;

	for (final var line : table) {

	    for (final var pos : posMap.entrySet()) {
		result[i][pos.getValue()] = line.get(pos.getKey());
	    }

	    i++;
	}

	LOGGER.debug("allPairWithValidValues succeeds with parameters {} and result length {}", parameters, result.length);

	return result;
    }
}
