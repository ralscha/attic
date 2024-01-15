

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.lib.*;

/**
 * prints statistics about a database file to System.out.
 * <br><br>Pass the database file path as an argument.
 * <br><br><b>This class is not part of db4o.jar!</b>
 * It is delivered as sourcecode in the
 * path ../com/db4o/tools/<br><br>
 */
public class Statistics {

	/**
	 * the main method that runs the statistics.
	 * @param String[] a String array of length 1, with the name of the database
	 * file as element 0.
	 */
	public static void main(String[] args) {
		Db4o.configure().messageLevel(-1);
		if (args == null || args.length != 1) {
			System.out.println("Usage: java com.db4o.tools.Statistics <database filename>");
		} else {
			new Statistics().run(args[0]);
		}
	}

	public void run(String filename) {
		if (new java.io.File(filename).exists()) {
			ObjectContainer con = null;
			try {
				con = Db4o.openFile(filename);
				printHeader("STATISTICS");
				System.out.println("File: " + filename);
				printStats(con, filename);
				con.close();
			} catch (Exception e) {
				System.out.println("Statistics failed for file: '" + filename + "'");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} else {
			System.out.println("File not found: '" + filename + "'");
		}
	}

	private void printStats(ObjectContainer con, String filename) {

		ScTree unavailable = new ScTreeString(REMOVE);
		ScTree noConstructor = new ScTreeString(REMOVE);

		final ScTreeInt[] ids = { new ScTreeInt(0)};
		// one element too many, substract one in the end

		StoredClass[] internalClasses = con.ext().storedClasses();
		for (int i = 0; i < internalClasses.length; i++) {
			try {
				Class clazz = Class.forName(internalClasses[i].getName());
				try {
					clazz.newInstance();
				} catch (Throwable th) {
					noConstructor =
						noConstructor.add(new ScTreeString(internalClasses[i].getName()));
				}
			} catch (Throwable t) {
				unavailable = unavailable.add(new ScTreeString(internalClasses[i].getName()));
			}
		}
		unavailable = unavailable.removeLike(new ScTreeString(REMOVE));
		noConstructor = noConstructor.removeLike(new ScTreeString(REMOVE));
		if (unavailable != null) {
			printHeader("UNAVAILABLE");
			unavailable.traverse(new Visitor4() {
				public void visit(Object obj) {
					System.out.println(((ScTreeString) obj).i_key);
				}
			});
		}
		if (noConstructor != null) {
			printHeader("NO PUBLIC CONSTRUCTOR");
			noConstructor.traverse(new Visitor4() {
				public void visit(Object obj) {
					System.out.println(((ScTreeString) obj).i_key);
				}
			});
		}

		printHeader("CLASSES");
		System.out.println("Number of objects per class:");

		if (internalClasses.length > 0) {
			ScTree all = new ScTreeStringObject(internalClasses[0].getName(), internalClasses[0]);
			for (int i = 1; i < internalClasses.length; i++) {
				all =
					all.add(
						new ScTreeStringObject(internalClasses[i].getName(), internalClasses[i]));
			}
			all.traverse(new Visitor4() {
				public void visit(Object obj) {
					ScTreeStringObject node = (ScTreeStringObject) obj;
					long[] newIDs = ((StoredClass)node.i_object).getIDs();
					for (int j = 0; j < newIDs.length; j++) {
						if (ids[0].find(new ScTreeInt((int) newIDs[j])) == null) {
							ids[0] = (ScTreeInt) ids[0].add(new ScTreeInt((int) newIDs[j]));
						}
					}
					System.out.println(node.i_key + ": " + newIDs.length);
				}
			});

		}

		printHeader("SUMMARY");
		System.out.println("File: " + filename);
		System.out.println("Stored classes: " + internalClasses.length);
		if (unavailable != null) {
			System.out.println("Unavailable classes: " + unavailable.size());
		}
		if (noConstructor != null) {
			System.out.println("Classes without public constructors: " + noConstructor.size());
		}
		System.out.println("Total number of objects: " + (ids[0].size() - 1));
	}
	
	private void printHeader(String str){
		int stars = (39 - str.length()) / 2;
		System.out.println("\n");
		for (int i = 0; i < stars; i++) {
			System.out.print("*");
		}
		System.out.print(" " + str + " ");
		for (int i = 0; i < stars; i++) {
			System.out.print("*");
		}
		System.out.println();
	}

	private static final String REMOVE = "XXxxREMOVExxXX";

}
