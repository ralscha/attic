package dustin.examples;

import com.google.common.base.Function;

/**
 * Simple Guava Function that converts provided object's toString() representation to
 * upper case.
 *
 * @author Dustin
 */
public class UpperCaseFunction<F, T> implements Function<F, T> {
	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object f) {
		return f.toString().toUpperCase();
	}
}
