package ch.rasc.smninfo.domain;

import java.util.Arrays;

public class Station {

	private String code;

	private String name;

	private double[] lngLat;

	private int[] kmCoordinates;

	private Integer altitude;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[] getLngLat() {
		return this.lngLat;
	}

	public void setLngLat(double[] lngLat) {
		this.lngLat = lngLat;
	}

	public int[] getKmCoordinates() {
		return this.kmCoordinates;
	}

	public void setKmCoordinates(int[] kmCoordinates) {
		this.kmCoordinates = kmCoordinates;
	}

	public Integer getAltitude() {
		return this.altitude;
	}

	public void setAltitude(Integer altitude) {
		this.altitude = altitude;
	}

	@Override
	public String toString() {
		return "Station [code=" + this.code + ", name=" + this.name + ", lngLat="
				+ Arrays.toString(this.lngLat) + ", kmCoordinates="
				+ Arrays.toString(this.kmCoordinates) + ", altitude=" + this.altitude
				+ "]";
	}

}
