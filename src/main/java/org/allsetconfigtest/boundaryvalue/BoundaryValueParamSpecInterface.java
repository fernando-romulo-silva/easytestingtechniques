package org.allsetconfigtest.boundaryvalue;

import java.util.Set;

import org.allsetconfigtest.ParamSpecInterface;

/**
 * @author Fernando Romulo da Silva
 *
 * @param <T>
 */
public interface BoundaryValueParamSpecInterface<T extends Comparable<T>> extends ParamSpecInterface<T> {

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
    T getNominalValue();
}
