package dustin.examples;

import static java.lang.System.out;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;

/**
 * Demonstrate use of Guava's Optional class.
 *
 * @author Dustin
 */
public class GuavaOptional {
	/** java.util.logging Logger handle. */
	private final static Logger LOGGER = Logger.getLogger(GuavaOptional.class
			.getCanonicalName());

	/** Map of state names to the names of that state's capital. */
	private final static Map<String, String> stateCapitals;

	static {
		final Map<String, String> tempStatesToCapitals = new HashMap<>();
		tempStatesToCapitals.put("Alaska", "Juneau");
		tempStatesToCapitals.put("Arkansas", "Little Rock");
		tempStatesToCapitals.put("Colorado", "Denver");
		tempStatesToCapitals.put("Idaho", "Boise");
		tempStatesToCapitals.put("Utah", "Salt Lake City");
		tempStatesToCapitals.put("Wyoming", "Cheyenne");
		stateCapitals = Collections.unmodifiableMap(tempStatesToCapitals);
	}

	/**
	 * Provide the name of the capital of the provided state. This method uses Guava's
	 * Optional.fromNullable(T) to ensure that a non-null Optional instance is always
	 * returned with a non-null contained reference or without a contained reference.
	 *
	 * @param stateName State whose capital is desired.
	 * @return Instance of Optional possibly containing the capital corresponding to
	 * provided the state name, if available.
	 */
	public Optional<String> getStateCapital(String stateName) {
		return Optional.fromNullable(stateCapitals.get(stateName));
	}

	/**
	 * Provide quotient resulting from dividing dividend by divisor.
	 *
	 * @param dividend Dividend used in division.
	 * @param divisor Divisor used in division.
	 * @return Optional wrapper potentially containing Quotient from dividing dividend by
	 * divisor.
	 */
	public Optional<BigDecimal> getQuotient(BigDecimal dividend, BigDecimal divisor) {
		BigDecimal quotient;
		try {
			quotient = dividend.divide(divisor);
		}
		catch (Exception ex) {
			String exStr = Throwables.getStackTraceAsString(ex);
			System.out.println(exStr);

			LOGGER.log(Level.SEVERE, "Unable to divide " + dividend + " by " + divisor
					+ "-", ex);
			quotient = null;
		}
		return Optional.fromNullable(quotient);
	}

	/**
	 * Main function for demonstrating Guava's optional class.
	 *
	 * @param arguments Command-line arguments; none expected.
	 */
	public static void main(String[] arguments) {
		final GuavaOptional me = new GuavaOptional();

		final String wyoming = "Wyoming";
		final Optional<String> wyomingCapitalWrapper = me.getStateCapital(wyoming);
		if (wyomingCapitalWrapper.isPresent()) {
			out.println("Capital of " + wyoming + " is " + wyomingCapitalWrapper.get());
		}
		out.println("Capital of " + wyoming + " is " + wyomingCapitalWrapper.orNull());

		final String northDakota = "North Dakota";
		final Optional<String> northDakotaCapitalWrapper = me
				.getStateCapital(northDakota);
		out.println("Capital of " + northDakota + " is " + northDakotaCapitalWrapper);
		out.println("Capital of " + northDakota + " is "
				+ northDakotaCapitalWrapper.or("Unspecified"));
		out.println("Capital of " + northDakota + " is "
				+ northDakotaCapitalWrapper.orNull());

		final Optional<String> nullOptional = me.getStateCapital(null);
		out.println("Capital of null is " + nullOptional);

		final BigDecimal dividend = new BigDecimal("5.0");
		final BigDecimal divisor = new BigDecimal("0.0");
		final Optional<BigDecimal> quotientWrapper = me.getQuotient(dividend, divisor);
		out.println("Quotient of " + dividend + " / " + divisor + " is "
				+ quotientWrapper);
	}
}
