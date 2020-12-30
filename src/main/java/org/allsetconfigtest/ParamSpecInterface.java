package org.allsetconfigtest;

import java.util.Set;

/**
 * @author Fernando Romulo da Silva
 *
 * @param <T>
 */
public interface ParamSpecInterface<T> {
    
    static final String MSG_INVALID_PARAMETERS = "Argument 'parameters' can't be empty or contain a null element!";
    
    /**
     * The parameter's name
     * 
     * @return A string with the name
     */
    String getName();

    /**
     * Return all parameter's values
     * 
     * @return a <code>Set</code> of <T>
     */
    Set<T> getValues();
}
