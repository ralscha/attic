package test;

import common.log.io.ObjectPrintWriter;
import java.util.*;

public class Airplane {
	private int numEngines = 4;
	private String manufacturer = "Cessna";
	private FlightState state = new FlightState(100.1);
	private List passengers = new ArrayList();
	private Airplane self;

	public int getNumEngines() {
		return numEngines;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public FlightState getState() {
		return state;
	}
	public List getPassengers() {
		return passengers;
	}
	public Airplane getSelf() {
		return self;
	}

	Airplane() {
		passengers.add(new Passenger("Chris"));
		passengers.add(new Passenger("Sarah"));
		passengers.add(new Passenger("Grace"));
		self = this;
	}
}
