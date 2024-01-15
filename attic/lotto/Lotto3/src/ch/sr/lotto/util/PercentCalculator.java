package ch.sr.lotto.util;

import java.text.*;

public class PercentCalculator {

	private double total;
	private DecimalFormat format;

	public PercentCalculator() {
		total = 0.0;

		format = new DecimalFormat("#,##0.0 %");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		format.setDecimalFormatSymbols(dfs);
	}

	public void clear() {
		total = 0.0;
	}

	public void add(int value) {
		total += value;
	}

	public void add(Integer value) {
		total += value.intValue();
	}

	public void add(double value) {
		total += value;
	}

	public String getPercentString(Integer value) {
		double percent = value.intValue() / total;
		return format.format(percent);
	}

	public String getPercentString(int value) {
		double percent = value / total;
		return format.format(percent);
	}
}