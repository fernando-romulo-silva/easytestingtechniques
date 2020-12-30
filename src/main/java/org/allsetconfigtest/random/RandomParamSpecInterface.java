package org.allsetconfigtest.random;

import java.util.Random;

import org.allsetconfigtest.ParamSpecInterface;

import com.google.common.collect.Iterables;

/**
 * @author Fernando Romulo da Silva
 *
 * @param <T>
 */
public interface RandomParamSpecInterface<T> extends ParamSpecInterface<T> {

    /**
     * @return
     */
    default T getRandomValue() {
	final var validValues = getValues();
	final var rnd = new Random();
	final var i = rnd.nextInt(validValues.size());
	return Iterables.get(validValues, i);
    }
}
