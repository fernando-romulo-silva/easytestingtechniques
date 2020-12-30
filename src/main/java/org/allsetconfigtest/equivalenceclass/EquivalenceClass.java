package org.allsetconfigtest.equivalenceclass;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.nonNull;
import static org.allsetconfigtest.equivalenceclass.EquivanceClassType.INVALID;
import static org.allsetconfigtest.equivalenceclass.EquivanceClassType.VALID;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class EquivalenceClass<T> {

    private final String name;

    private final Predicate<T> rule;

    private final EquivanceClassType type;

    private final Set<T> values = new LinkedHashSet<>();

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static <T> EquivalenceClass<T> newValidClass(final Predicate<T> rule) {
	return new EquivalenceClass<T>(rule, VALID);
    }

    public static <T> EquivalenceClass<T> newInvalidClass(final Predicate<T> rule) {
	return new EquivalenceClass<T>(rule, INVALID);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static <T> EquivalenceClass<T> newValidClass(final String name, final Predicate<T> rule) {
	return new EquivalenceClass<T>(name, rule, VALID);
    }

    public static <T> EquivalenceClass<T> newInvalidClass(final String name, final Predicate<T> rule) {
	return new EquivalenceClass<T>(name, rule, INVALID);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------

    private EquivalenceClass(final Predicate<T> rule, final EquivanceClassType type) {
	super();
	checkArgument(nonNull(type), "Parameter 'type' is invalid! It's cannot be null.");
	checkArgument(nonNull(rule), "Parameter 'rule' is invalid! It's cannot be null.");
	this.name = UUID.randomUUID().toString();
	this.rule = rule;
	this.type = type;

    }

    private EquivalenceClass(final String name, final Predicate<T> rule, final EquivanceClassType type) {
	super();
	checkArgument(nonNull(type), "Parameter 'type' is invalid! It's cannot be null.");
	checkArgument(nonNull(rule), "Parameter 'rule' is invalid! It's cannot be null.");
	checkArgument(isNotBlank(name), "Parameter 'name' is invalid!  It's cannot be null or empty.");
	this.name = name;
	this.rule = rule;
	this.type = type;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------

    public String getName() {
	return name;
    }

    public Predicate<T> getRule() {
	return rule;
    }

    public boolean isValid() {
	return type == VALID ? true : false;
    }
    
    public boolean isInvalid() {
	return type == INVALID ? true : false;
    }


    /**
     * @return
     */
    public T getDefaultValue() {

	final var valuesList = new ArrayList<>(values);

	final var mid = (0 + valuesList.size()) >>> 1;

	return valuesList.get(mid);
    }

    /**
     * @return
     */
    public T getRandomValue() {

	final var rand = new Random();

	final var valuesList = new ArrayList<>(values);

	return valuesList.get(rand.nextInt(values.size()));
    }

    /**
     * @return
     */
    public Set<T> getValues() {
	return unmodifiableSet(values);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------

    void loadValues(final Set<T> newValues) {
	checkArgument(isNotEmpty(newValues), "Parameter 'newValues' is invalid!");
	values.clear();
	values.addAll(newValues);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	return new HashCodeBuilder() //
		.append(this.name) //
		.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @SuppressWarnings("preview")
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}

	if (obj instanceof EquivalenceClass<?> other) {
	    return new EqualsBuilder() //
		    .append(this.name, other.name) //
		    .isEquals();
	} else {
	    return false;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return new ToStringBuilder(this, SHORT_PREFIX_STYLE) //
		.append("name", name) //
		.append("type", type) //
		.append("values", values) //
		.toString();
    }

}
