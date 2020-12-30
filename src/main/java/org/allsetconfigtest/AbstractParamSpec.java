package org.allsetconfigtest;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbstractParamSpec<T, R extends AbstractParamSpec<T, R>> implements ParamSpecInterface<T> {

    protected final String name;

    /**
     * Default constructor
     */
    protected AbstractParamSpec() {
	super();
	this.name = randomUUID().toString();
    }

    /**
     * Default constructor
     * 
     * @param name
     */
    protected AbstractParamSpec(final String name) {
	super();
	checkArgument(isNotBlank(name), "Parameter 'name' can't be null!");
	this.name = name;
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    protected R getThis() {
	return (R) this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.allsetconfigtest.combinatorial.parameter.CombinatorialParameterInterface#getName()
     */
    @Override
    public String getName() {
	return name;
    }

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
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}

	if (obj instanceof AbstractParamSpec<?, ?> other) {
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
		.append("values", join(getValues(), "\t")) //
		.toString();
    }
}
