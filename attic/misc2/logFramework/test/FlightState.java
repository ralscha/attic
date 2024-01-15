package test;

import common.log.io.ObjectPrintWriter;

public class FlightState {
	private double speed;

	public FlightState(double speed) {
		this.speed = speed;
	}
	public double getSpeed() {
		return speed;
	}

	
	public void printTo(ObjectPrintWriter writer) {
		writer.print("speed", speed);
	}
	
}