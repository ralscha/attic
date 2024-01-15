package test;

import java.util.*;
import common.log.io.ObjectPrintWriter;
import common.log.io.JavaPrintWriter;

public class JavaPrintWriterTest extends Base {
	public JavaPrintWriterTest() {
		ObjectPrintWriter writer = new JavaPrintWriter(System.out, true);
		writer.setPrettyOutput(true);

		Vector v = new Vector();
		v.addElement("element 1");
		v.addElement("element 2");
		v.addElement("element 3");
		writer.print("vector", v);
		writer.flush();

		writer = new JavaPrintWriter(System.out, true);
		writer.setPrettyOutput(true);
		Airplane airplane = new Airplane();
		writer.print("airplane", airplane);
		writer.flush();

		writer = new JavaPrintWriter(System.out, true);
		writer.setPrettyOutput(true);
		Hashtable movies = new Hashtable();
		movies.put("grace kelly", "To Catch a Thief");
		movies.put("Cary Grant", "Notorious");
		movies.put("Lana Turner", "Lady From Shanghai");
		writer.print("movies", movies);
		writer.flush();

		writer = new JavaPrintWriter(System.out, true);
		writer.setPrettyOutput(true);
		String[] countries = { "USA", "France", "Switzerland" };
		writer.print("countries", countries);
		writer.flush();
	}

	public static void main(String[] args) {
		new JavaPrintWriterTest();
	}
}

