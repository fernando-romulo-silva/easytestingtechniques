package org.allsetconfigtest.random;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Integer.MIN_VALUE;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.text.RandomStringGenerator;

/**
 * @author Fernando Romulo da Silva
 */
public final class StringRandomParamSpec extends AbstractRandomParamSpec<String, StringRandomParamSpec> {

    private final StringParameterDetails details;

    // --------------------------------------------------------------------------------------------------------------

    public static StringRandomParamSpec ofString() {
	return new StringRandomParamSpec();
    }

    public static StringRandomParamSpec ofString(final String name) {
	return new StringRandomParamSpec(name);
    }

    public static StringRandomParamSpec ofString(final String name, final int qtyValues, final Consumer<StringParameterDetails.Builder> function) {
	final var details = StringParameterDetails.Builder.of(function);
	return new StringRandomParamSpec(name, qtyValues, details);
    }

    public static StringRandomParamSpec ofString(final String name, final Consumer<StringParameterDetails.Builder> function) {
	final var details = StringParameterDetails.Builder.of(function);
	return new StringRandomParamSpec(name, details);
    }

    // --------------------------------------------------------------------------------------------------------------

    private StringRandomParamSpec() {
	super();
	this.details = new StringParameterDetails.Builder().build();
    }

    private StringRandomParamSpec(final String name) {
	super(name);
	this.details = new StringParameterDetails.Builder().build();
    }

    private StringRandomParamSpec(final String name, final StringParameterDetails details) {
	super(name);
	this.details = details;
    }

    private StringRandomParamSpec(final String name, final int qtyValues, final StringParameterDetails details) {
	super(name, qtyValues);
	this.details = details;
    }

    // --------------------------------------------------------------------------------------------------------------

    @Override
    protected void generateValues() {

	if (isNotEmpty(values)) {
	    return;
	}

	final var pairsInt = new ArrayList<List<Integer>>();

	// 0 .. 9
	if (details.numbers) {
	    pairsInt.add(List.of(48, 57));// 48 .. 57
	}

	// A .. Z
	if (details.upperLetters) {
	    pairsInt.add(List.of(65, 90)); // 65 .. 90
	}

	// a .. z
	if (details.lowerLetters) {
	    pairsInt.add(List.of(97, 122)); // 97 .. 122
	}

	if (details.specialChar) {
	    pairsInt.add(List.of(0, 31)); // command chars
	    pairsInt.add(List.of(32, 47)); //
	    pairsInt.add(List.of(58, 64)); //
	    pairsInt.add(List.of(91, 96)); //
	    pairsInt.add(List.of(123, 126)); //
	}

	final var pairs = new char[pairsInt.size()][2];

	var i = 0;

	for (final var list : pairsInt) {
	    pairs[i][0] = (char) list.get(0).intValue();
	    pairs[i][1] = (char) list.get(1).intValue();
	    i++;
	}

	final var generator = new RandomStringGenerator.Builder() //
//		.selectFrom("ovo".toCharArray()) //
		.withinRange(pairs)//
		.build();

	i = 1;

	var done = false;

	while (done == false) {

	    if (i >= qtyValues * 3) {
		done = true;
	    } else if (values.size() >= qtyValues) {
		done = true;
	    } else {
		values.add(generator.generate(details.minLength, details.maxLength));
	    }

	    i++;
	}
    }

    public static final class StringParameterDetails {

	private final boolean specialChar;

	private final boolean numbers;

	private final boolean upperLetters;

	private final boolean lowerLetters;

	private final int minLength;

	private final int maxLength;

	private StringParameterDetails(final Builder builder) {
	    this.specialChar = builder.specialChar;
	    this.numbers = builder.numbers;
	    this.upperLetters = builder.upperLetters;
	    this.lowerLetters = builder.lowerLetters;
	    this.minLength = builder.minLength;
	    this.maxLength = builder.maxLength;
	}

	public static final class Builder {

	    private static StringParameterDetails of(final Consumer<Builder> function) {

		checkArgument(nonNull(function), "Parameter 'function' can't be null!");

		return new Builder().with(function).build();
	    }

	    private Builder() {
		super();
	    }

	    public boolean specialChar = false;

	    public boolean numbers = false;

	    public boolean upperLetters = true;

	    public boolean lowerLetters = true;

	    public int minLength = MIN_VALUE;

	    public int maxLength = 5;

	    private Builder with(final Consumer<Builder> function) {
		function.accept(this);
		return this;
	    }

	    private StringParameterDetails build() {

		if (minLength == MIN_VALUE) {
		    minLength = maxLength;
		}

		checkArgument(minLength > 1, "Field 'minLength' can't be less than zero!");

		checkArgument(maxLength > 1, "Field 'maxLength' can't be less than zero!");

		return new StringParameterDetails(this);
	    }
	}
    }
}
