package org.allsetconfigtest.combinatorial;

import java.util.Random;
import java.util.Set;

import org.allsetconfigtest.ParamSpecInterface;

import com.google.common.collect.Iterables;

/**
 * Class that represent a parameter of method test.
 * 
 * Parameters refers to the list of variables in a method declaration. </br>
 * Arguments are the actual values that are passed in when the method is invoked. </br>
 * When you invoke a method, the arguments used must match the declaration's parameters in type and order. </br>
 * 
 * @author Fernando Romulo da Silva
 * 
 * @param <T> The type of implementation
 */
public interface CombinatorialParamSpecInterface<T> extends ParamSpecInterface<T> {

    /**
     * @return
     */
    T getDefaultValidValue();

    /**
     * @return
     */
    T getDefaultInvalidValue();

    /**
     * The valid values of parameter
     * 
     * @return a <code>Set</code> of <T>
     */
    Set<T> getValidValues();

    /**
     * The invalid values of parameter
     * 
     * @return a <code>Set</code> of <T>
     */
    Set<T> getInvalidValues();

    /**
     * @return
     */
    default T getRandomValidValue() {
	final var validValues = getValidValues();
	final var rnd = new Random();
	final var i = rnd.nextInt(validValues.size());
	return Iterables.get(validValues, i);
    }

    /**
     * @return
     */
    default T getRandomInvalidValue() {
	final var invalidValues = getInvalidValues();
	final var rnd = new Random();
	final var i = rnd.nextInt(invalidValues.size());
	return Iterables.get(invalidValues, i);
    }
}
