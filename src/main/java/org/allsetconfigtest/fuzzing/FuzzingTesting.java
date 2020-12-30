package org.allsetconfigtest.fuzzing;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;

public class FuzzingTesting {

    private static final Logger LOGGER = getLogger(FuzzingTesting.class);

    /**
     * Default constructor.
     */
    private FuzzingTesting() {
	throw new Error("You cant instance that class!");
    }

    /**
     * @param parameters
     * @return
     */
    public static Object[][] smartFuzzingTesting(final FuzzingParamSpecInterface<?>... parameters) {

	LOGGER.debug("smartFuzzingTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, "Argument 'parameters' is invalid!");

	final var result = new LinkedHashSet<List<Object>>();
	
	
	LOGGER.debug("smartFuzzingTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }
    
    /**
     * @param parameters
     * @return
     */
    public static Object[][] dumbFuzzingTesting(final FuzzingParamSpecInterface<?>... parameters) {

	LOGGER.debug("dumbFuzzingTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, "Argument 'parameters' is invalid!");

	final var result = new LinkedHashSet<List<Object>>();
	
	
	LOGGER.debug("dumbFuzzingTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }    
    
    
//    Null characters
//    New line characters
//    Semi-colons
//    Format string values (%n, %s, etc.)
//    Application specific keywords
//  
    // Very long or completely blank strings
    // Maximum and minimum values for integers on the platform 
    // Values like -1, 0, 1, and 2
    
    
    /**
     * @param parameters
     * @return
     */
    public static Object[][] mutationFuzzingTesting(final FuzzingParamSpecInterface<?>... parameters) {

	LOGGER.debug("worstNormalBoundaryValueTesting begins with parameters {}", (Object[]) parameters);

	checkArgument(isNotEmpty(parameters) && contains(parameters, null) == false, "Argument 'parameters' is invalid!");

	final var result = new LinkedHashSet<List<Object>>();
	
	
	LOGGER.debug("worstNormalBoundaryValueTesting succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }  
    
    
}
