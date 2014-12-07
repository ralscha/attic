package ch.rasc.cartracker.report;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@Model(value = "CarTracker.model.report.Month",
		readMethod = "reportService.readMonthReport")
public class Month implements Comparable<Month> {
	@ModelField(persist = false)
	private String month;

	@ModelField(persist = false)
	private int totalSold;

	@ModelField(persist = false)
	private int totalSales;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getTotalSold() {
		return totalSold;
	}

	public void setTotalSold(int totalSold) {
		this.totalSold = totalSold;
	}

	public int getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(int totalSales) {
		this.totalSales = totalSales;
	}

	@Override
	public int compareTo(Month o) {
		return month.compareTo(o.month);
	}

}
