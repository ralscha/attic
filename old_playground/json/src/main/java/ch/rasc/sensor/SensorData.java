package ch.rasc.sensor;

import java.util.LinkedHashMap;
import java.util.Map;

public class SensorData {
	private final String sensorId;

	private final Map<Long, int[]> measurements;

	public SensorData(String sensorId) {
		this.sensorId = sensorId;
		this.measurements = new LinkedHashMap<>();
	}

	public void addMeasurement(Long seconds, int[] measurement) {
		this.measurements.put(seconds, measurement);
	}

	public String getSensorId() {
		return this.sensorId;
	}

	public Map<Long, int[]> getMeasurements() {
		return this.measurements;
	}

}
