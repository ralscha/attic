package test;

import java.util.Date;
import java.text.DecimalFormat;


public class Timer {
	private Date start;
	private long duration = 0;
	private static DecimalFormat hoursFormat = new DecimalFormat("00");
	private static DecimalFormat minutesFormat = hoursFormat;
	private static DecimalFormat secondsFormat = new DecimalFormat("00.###");


	public Timer() {}

	public Timer(Timer toCopy) {
		if (toCopy != null) {
			if (toCopy.start == null)
				start = null;
			else
				start = new Date(toCopy.start.getTime());

			duration = toCopy.duration;
		}
	}

	public void start() {
		if (start == null)
			start = new Date();
	}

	public void stop() {
		if (start != null) {
			Date stop = new Date();
			duration += stop.getTime() - start.getTime();
			start = null;
		}
	}

	public boolean isStopped() {
		return start == null;
	}

	public void zero() {
		duration = 0;
		if (start != null)
			start = new Date();
	}

	public long durationInMilliSeconds() {
		if (start == null)
			return duration;
		else {
			Date now = new Date();
			return now.getTime() - start.getTime() + duration;
		}
	}

	public double duration() {
		return durationInMilliSeconds() / 1000.0;
	}

	public String toString() {
		double seconds = duration();
		StringBuffer result = new StringBuffer();

		if (!isStopped())
			result.append("*");

		if (seconds > 60.0) {
			long minutes = (long)(seconds / 60.0);

			if (minutes > 60) {
				long hours = minutes / 60;
				result.append(hoursFormat.format(hours) + ":");
			}

			result.append(minutesFormat.format(minutes) + ":");
			result.append(secondsFormat.format(seconds));
		} else {
			result.append("" + seconds);
		}

		return result.toString();
	}


	public static void main(String[] args) {
		Timer t = new Timer();

		t.start();

		try {
			Thread.sleep(1230);
		} catch (Exception e) {}

		System.out.println("should be about 1.23 :" + t);

		try {
			Thread.sleep(100);
		} catch (Exception e) {}

		t.stop();
		System.out.println("should be about 1.33 : " + t);

		try {
			Thread.sleep(100);
		} catch (Exception e) {}

		System.out.println("should still be about 1.33 : " + t);
		try {
			Thread.sleep(100);
		} catch (Exception e) {}
		System.out.println("should still be about 1.33 : " + t);


		t.start();

		try {
			Thread.sleep(100);
		} catch (Exception e) {}

		System.out.println("should be about 1.43 : " + t);
		t.zero();

		try {
			Thread.sleep(100);
		} catch (Exception e) {}

		System.out.println("should be about 0.1 : " + t);
	}
}

