package ch.rasc.cartracker.report;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@Model(value = "CarTracker.model.report.Make",
		readMethod = "reportService.readMakeReport")
public class Make implements Comparable<Make> {
	@ModelField(persist = false)
	private String make;

	@ModelField(persist = false)
	private String model;

	@ModelField(persist = false)
	private int totalSales;

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(int totalSales) {
		this.totalSales = totalSales;
	}

	@Override
	public int compareTo(Make o) {
		return make.compareTo(o.make);
	}

}
