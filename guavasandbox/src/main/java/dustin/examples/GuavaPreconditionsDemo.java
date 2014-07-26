package dustin.examples;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkPositionIndex;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.System.err;

/**
 * Simple demonstration of Guava's Preconditions support.
 *
 * @author Dustin
 */
public class GuavaPreconditionsDemo {
	private final boolean initialized = false;

	/**
	 * Demonstrate Guava's Preconditions.checkNotNull methods.
	 *
	 * @param parameter Parameter that is checked for null-ness.
	 */
	public void testForNonNullArgument(String parameter) {
		@SuppressWarnings("unused")
		final String localParameter = checkNotNull(parameter,
				"Provided parameter is unacceptably null.");
	}

	public void testDivisorNotZero(int divisor) {
		checkArgument(divisor != 0, "Zero divisor not allowed.");
	}

	public void testArrayElement(String[] strArray, int indexNumber) {
		@SuppressWarnings("unused")
		final int index = checkElementIndex(indexNumber, strArray.length,
				"String array index number");
	}

	public void testArrayPosition(String[] strArray, int indexNumber) {
		@SuppressWarnings("unused")
		final int index = checkPositionIndex(indexNumber, strArray.length,
				"String array index number");
	}

	public void testState() {
		checkState(this.initialized, "Cannot perform action because not initialized.");
	}

	public static void printHeader(String newHeaderText) {
		err.println("\n==========================================================");
		err.println("== " + newHeaderText);
		err.println("==========================================================");
	}

	/**
	 * Main function for executing demonstrations of Guava's Preconditions.
	 */
	public static void main(String[] arguments) {
		final GuavaPreconditionsDemo me = new GuavaPreconditionsDemo();

		printHeader("Preconditions.checkNotNull");
		try {
			me.testForNonNullArgument(null);
		}
		catch (NullPointerException npe) {
			npe.printStackTrace();
		}

		printHeader("Preconditions.checkArgument");
		try {
			me.testDivisorNotZero(0);
		}
		catch (IllegalArgumentException illArgEx) {
			illArgEx.printStackTrace();
		}

		printHeader("Preconditions.checkElementIndex");
		try {
			me.testArrayElement(new String[] { "Dustin", "Java" }, 3);
		}
		catch (IndexOutOfBoundsException ioobEx) {
			ioobEx.printStackTrace();
		}

		printHeader("Preconditions.checkPositionIndex");
		try {
			me.testArrayPosition(new String[] { "Dustin", "Java" }, 3);
		}
		catch (IndexOutOfBoundsException ioobEx) {
			ioobEx.printStackTrace();
		}

		printHeader("Preconditions.checkState");
		try {
			me.testState();
		}
		catch (IllegalStateException illStateEx) {
			illStateEx.printStackTrace();
		}
	}
}
