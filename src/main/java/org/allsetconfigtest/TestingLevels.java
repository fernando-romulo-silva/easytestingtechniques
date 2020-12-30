package org.allsetconfigtest;

/**
 * Constant for testing levels.
 * 
 * @author Fernando Romulo da Silva
 */
public final class TestingLevels {

    /**
     * Unit tests.
     */
    public static final String UNIT = "unit";

    /**
     * Integration tests.
     */
    public static final String INTEGRATION = "integration";

    /**
     * Functional tests.
     */
    public static final String SYSTEM = "system";

    /**
     * Acceptance tests.
     */
    public static final String ACCEPTANCE = "acceptance";

    /**
     * Default constructor.
     */
    private TestingLevels() {
        throw new Error("You cant instance that class!");
    }
}
