package org.allsetconfigtest.combinatorial;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.cartesianProduct;
import static java.util.Collections.replaceAll;
import static org.allsetconfigtest.ParamSpecInterface.MSG_INVALID_PARAMETERS;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;

/**
 * Util class that create different combinations of values for testing.
 * 
 * @author Fernando Romulo da Silva
 */
public final class CombinatorialTesting {
    
    private static final Logger LOGGER = getLogger(CombinatorialTesting.class);

    /**
     * Default constructor.
     */
    private CombinatorialTesting() {
	throw new Error("You cant instance that class!");
    }

    /**
     * Create a matrix of objects to use it for validation parameter tests. Example with TestNG:
     * 
     * <pre>
     * // @formatter:off
     * &#64;DataProvider(name = "yourMethodData")
     * public Object[][] yourMethodData() {
     *     return combinationsWithInvalidValuesAndNominalValue( 
     *                                                           valueOf("test1", "", null), 
     *                                                           valueOf("test2", "", null), 
     *                                                           valueOf(new int[]{ 8080 }, null, new int[]{}), 
     *                                                           valueOf(new String[]{ "MYSQL_ROOT_PASSWORD=root" }, null, new String[]{}, new String[]{ null, "test=test" }), 
     *                                                           valueOf("src/test/resources", "", null, "is not a dir"), 
     *                                                         );
     * }
     * // @formatter:on
     * </pre>
     * 
     * That will be create a matrix:
     * 
     * <pre>
     * 
     * { 
     *   { ""     , "test2", new int[]{ 8080 }, new String[]{ "MYSQL_ROOT_PASSWORD=root" }, "src/test/resources" }, 
     *   { null   , "test2", new int[]{ 8080 }, new String[]{ "MYSQL_ROOT_PASSWORD=root" }, "src/test/resources" }, 
     *   { "test1", ""     , new int[]{ 8080 }, new String[]{ "MYSQL_ROOT_PASSWORD=root" }, "src/test/resources" }, 
     *   { "test1", null   , new int[]{ 8080 }, new String[]{ "MYSQL_ROOT_PASSWORD=root" }, "src/test/resources" }, 
     *   { "test1", "test2", null             , new String[]{ "MYSQL_ROOT_PASSWORD=root" }, "src/test/resources" }, 
     *   { "test1", "test2", new int[]{}      , new String[]{ "MYSQL_ROOT_PASSWORD=root" }, "src/test/resources" }, 
     *   { "test1", "test2", new int[]{ 8080 }, null                                      , "src/test/resources" }, 
     *   { "test1", "test2", new int[]{ 8080 }, new String[]{}                            , "src/test/resources" }, 
     *   { "test1", "test2", new int[]{ 8080 }, new String[]{ null, "test=test" }         , "src/test/resources" }, 
     *   { "test1", "test2", new int[]{ 8080 }, new String[]{ "MYSQL_ROOT_PASSWORD=root" }, ""                   }, 
     *   { "test1", "test2", new int[]{ 8080 }, new String[]{ "MYSQL_ROOT_PASSWORD=root" }, null                 }, 
     *   { "test1", "test2", new int[]{ 8080 }, new String[]{ "MYSQL_ROOT_PASSWORD=root" }, "is not a dir"       } 
     * }
     * 
     * </pre>
     * 
     * The each rows contains the param's value of the method test. Each column is associated with each param of method test.
     * 
     * <pre>
     * // @formatter:off
     * &#64;Test(priority = 1, groups = UNIT, expectedExceptions = { IllegalArgumentException.class }, dataProvider = "yourMethodData")
     * public void yourMethodTest(final String parameter1, final String parameter2, final int[] parameter3, final String[] parameter4, final String parmeter5) { // five parameters 
     *     final YourClass yourClass = new YourClass(); 
     *     yourClass.yourMethod(parameter1, parameter2, parameter3, parameter4, parameter5); 
     * }
     * // @formatter:on
     * </pre>
     * 
     * @param parameters
     *            A vetor of <code>CombinatorialParamSpecInterface<?></code> objects. The vector is all the possible values of the parameter
     * @return A Object[][] object with the combinations
     */
    public static Object[][] combinationsWithValidValueAndInvalidValues(final CombinatorialParamSpecInterface<?>... parameters) {

	LOGGER.debug("combinationsWithInvalidValuesAndValidValue begins with parameters {}", (Object[]) parameters);
	
	checkArgument(isNotEmpty(parameters) && !contains(parameters, null), MSG_INVALID_PARAMETERS);

	final var allValidValues = new ArrayList<Object>();
	final var allInvalidValues = new ArrayList<Object>();

	for (final var parameter : parameters) {
	    allValidValues.add(parameter.getValidValues());
	    allInvalidValues.addAll(parameter.getInvalidValues());
	}

	final var rowValid = new Object[parameters.length];

	final var listResult = new ArrayList<Object[]>();

	var i = 0;

	for (final var parameter : parameters) {
	    rowValid[i++] = parameter.getDefaultValidValue();
	}

	i = 0;

	for (final var parameter : parameters) {

	    Object[] rowTemp = null;

	    for (final var object : parameter.getInvalidValues()) {
		rowTemp = rowValid.clone();
		rowTemp[i] = object;
		listResult.add(rowTemp);
	    }

	    i++;
	}

	final var result = new Object[listResult.size()][parameters.length];

	for (i = 0; i < result.length; i++) {
	    result[i] = listResult.get(i);
	}

	LOGGER.debug("combinationsWithInvalidValuesAndValidValue succeeds with parameters {} and result length {}", parameters, result.length);

	return result;
    }

    public static Object[][] combinationsWithValidValues(final CombinatorialParamSpecInterface<?>... parameters) {

	LOGGER.debug("combinationsWithValidValues begins with parameters {}", (Object[]) parameters);
	
	checkArgument(isNotEmpty(parameters) && !contains(parameters, null), MSG_INVALID_PARAMETERS);

	final var result = new LinkedHashSet<List<Object>>();

	final var lists = new ArrayList<List<Object>>();

	for (final var parameter : parameters) {
	    lists.add(new ArrayList<>(parameter.getValidValues()));
	}

	result.addAll(cartesianProduct(lists));

	LOGGER.debug("combinationsWithValidValues succeeds with parameters {} and result length {}", parameters, result.size());

	return result.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }

    public static Object[][] combinationsWithValidValuesAndInvalidValues(final CombinatorialParamSpecInterface<?>... parameters) {
	
	LOGGER.debug("combinationsWithValidValuesAndInvalidValues begins with parameters {}", (Object[]) parameters);
	
	checkArgument(isNotEmpty(parameters) && !contains(parameters, null), MSG_INVALID_PARAMETERS);

	final var result = new LinkedHashSet<List<Object>>();

	final var object = new Object();

	final var lists = new ArrayList<List<Object>>();

	for (final var parameter : parameters) {
	    final var values = new ArrayList<Object>(parameter.getValues());
	    replaceAll(values, null, object);
	    lists.add(values);
	}

	result.addAll(cartesianProduct(lists));

	final var resultTemp = new LinkedHashSet<List<Object>>();

	result.forEach(list -> resultTemp.add(new ArrayList<>(list)));

	resultTemp.forEach(list -> replaceAll(list, object, null));
	
	LOGGER.debug("combinationsWithValidValuesAndInvalidValues succeeds with parameters {} and result length {}", parameters, result.size());

	return resultTemp.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }
}
