package dustin.examples;

import static java.lang.System.out;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Stopwatch;

/**
 * Demonstrates Guava's (Release 10) Stopwatch class.
 *
 * @author Dustin
 */
public class StopWatchDemo {
	private final static Logger LOGGER = Logger.getLogger(StopWatchDemo.class
			.getCanonicalName());

	public static void doSomethingJustToBeDoingIt(int numberOfTimesToDoNothing) {
		for (int count = 0; count < numberOfTimesToDoNothing; count++) {
			try {
				Thread.sleep(TimeUnit.SECONDS.toMillis(1));
			}
			catch (InterruptedException interruptEx) {
				LOGGER.log(Level.INFO, "Don't interrupt me when I'm trying to sleep!",
						interruptEx);
			}
		}
	}

	/**
	 * Print statistics on Stopwatch-reported times for provided number of loops.
	 *
	 * @param numberLoops Number of loops executed.
	 * @param stopwatch Stopwatch instance with time used statistics.
	 */
	public static void printElapsedTime(int numberLoops, Stopwatch stopwatch) {
		if (stopwatch.isRunning()) {
			out.println("WARNING! Your stopwatch is still running!");
		}
		else // stopwatch not running
		{
			out.println(numberLoops + " loops required: ");
			out.println("\t" + stopwatch.toString());
			out.println("\t" + stopwatch.elapsed(TimeUnit.MILLISECONDS)
					+ " elapsed milliseconds.");
			out.println("\t" + stopwatch.elapsed(TimeUnit.MINUTES) + " minutes");
			out.println("\t" + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds");
			out.println("\t" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " milliseconds");
			out.println("\t" + stopwatch.elapsed(TimeUnit.NANOSECONDS) + " nanoseconds");
		}
	}

	public static void main(String[] arguments) {
		final Stopwatch stopwatch = Stopwatch.createStarted();

		int numberTimes = 5;
		stopwatch.start();
		doSomethingJustToBeDoingIt(numberTimes);
		stopwatch.stop();
		printElapsedTime(numberTimes, stopwatch);

		numberTimes = 45;
		stopwatch.reset();
		stopwatch.start();
		doSomethingJustToBeDoingIt(numberTimes);
		stopwatch.stop();
		printElapsedTime(numberTimes, stopwatch);

		numberTimes = 125;
		stopwatch.reset();
		stopwatch.start();
		doSomethingJustToBeDoingIt(numberTimes);
		stopwatch.stop();
		printElapsedTime(numberTimes, stopwatch);
	}
}
