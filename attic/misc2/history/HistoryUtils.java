

import java.io.*;
import java.util.Hashtable;

public class HistoryUtils {

	public static void saveToLocalDisk(String fileName, Hashtable table) throws IOException {
		saveToLocalDisk(new File(fileName), table);
	}

	public static Hashtable loadFromLocalDisk(String fileName) throws IOException {
		return loadFromLocalDisk(new File(fileName));
	}

	public static void saveToLocalDisk(File file, Hashtable table) throws IOException {

		if (file.exists()) {
			file.delete(); //remove old history
		}

		ObjectOutputStream oOut = new ObjectOutputStream(new FileOutputStream(file));

		oOut.writeObject(table);
		oOut.flush();
		oOut.close();
	}

	public static Hashtable loadFromLocalDisk(File file) throws IOException {

		Hashtable table = null;

		if (file.exists()) {
			ObjectInputStream oIn = new ObjectInputStream(new FileInputStream(file));

			try {
				Object o = oIn.readObject();
				table = (Hashtable) o;
			} catch (ClassCastException ex) {
				ex.printStackTrace();
			}
			catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		} else {
			table = new Hashtable();
		}
		return table;
	}
}