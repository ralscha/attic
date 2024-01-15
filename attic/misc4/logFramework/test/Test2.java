package test;


import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;
import common.log.io.*;
import common.log.*;

public class Test2 {
	class Airplane {

		public int getNumEngines() {
			return numEngines;
		}

		public String getManufacturer() {
			return manufacturer;
		}

		public double getSpeed() {
			return speed;
		}

		public Vector getPassengers() {
			return passengers;
		}

		public Airplane getSelf() {
			return self;
		}

		private int numEngines;
		private String manufacturer;
		private double speed;
		private Vector passengers;
		private Airplane self;

		public Airplane() {
			numEngines = 4;
			manufacturer = "Cessna";
			speed = 100.40000000000001D;
			passengers = new Vector();
			passengers.addElement("Chris");
			passengers.addElement("Sarah");
			passengers.addElement("Grace");
			self = this;
		}
	}


	public Test2() {
		ObjectPrintWriter writer = new JavaPrintWriter(System.out, true);
		writer.setPrettyOutput(true);
		Vector v = new Vector();
		v.addElement("element 1");
		v.addElement("element 2");
		v.addElement("element 3");
		writer.print("vector", v);
//		writer.separateObjectItems();
		Airplane airplane = new Airplane();
		writer.print("airplane", airplane);
//		writer.separateObjectItems();
		Hashtable movies = new Hashtable();
		movies.put("grace kelly", "To Catch a Thief");
		movies.put("Cary Grant", "Notorious");
		movies.put("Lana Turner", "Lady From Shanghai");
		writer.print("movies", movies);
//		writer.separateObjectItems();
		String countries[] = { "USA", "France", "Switzerland" };
		writer.print("countries", countries);
		writer.flush();
	}

	public static void main(String args[]) {
		new Test2();
	}
}