package org.allsetconfigtest.equivalenceclass;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class SpecRange<T extends Number & Comparable<T>> {

    private final T minValue;

    private final T maxValue;

    private final T increment;

    public static <R extends Number & Comparable<R>> SpecRange<R> ofRange(final R minValue, final R maxValue, final R increment) {
	return new SpecRange<R>(minValue, maxValue, increment);
    }

    private SpecRange(final T minValue, final T maxValue, final T increment) {
	super();
	checkArgument(nonNull(minValue), "The parameter 'minValue' can't be null!");
	checkArgument(nonNull(maxValue), "The parameter 'maxValue' can't be null!");
	checkArgument(nonNull(increment), "The parameter 'increment' can't be null!");

	this.minValue = minValue;
	this.maxValue = maxValue;
	this.increment = increment;
    }

//    private SpecRange(final T minValue, final T maxValue) {
//	this(minValue, maxValue, NumberUtils.num);
//    }

    public T getMinValue() {
	return minValue;
    }

    public T getMaxValue() {
	return maxValue;
    }

    public T getIncrement() {
	return increment;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	return new HashCodeBuilder() //
		.append(this.minValue) //
		.append(this.maxValue) //
		.append(this.increment) //
		.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}

	if (obj instanceof SpecRange<?> other) {
	    return new EqualsBuilder() //
		    .append(this.minValue, other.minValue) //
		    .append(this.maxValue, other.maxValue) //
		    .append(this.increment, other.increment) //
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
		.append("minValue", minValue) //
		.append("maxValue", maxValue) //
		.append("increment", increment) //
		.toString();
    }
}
