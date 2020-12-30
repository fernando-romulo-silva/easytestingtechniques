package org.allsetconfigtest;

import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface ValuesSupplier<T> extends Supplier<List<T>> {

}
