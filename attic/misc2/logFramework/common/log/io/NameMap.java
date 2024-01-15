package common.log.io;

import java.util.*;

public class NameMap {
	private String prefix = "";
	private String appendix = "";
	private int nextId = 1;
	private Hashtable table = new Hashtable();

	public NameMap() {}
	public NameMap(String prefix) {
		this.prefix = prefix;
	}
	public NameMap(String prefix, String appendix) {
		this.prefix = prefix;
		this.appendix = appendix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	protected String newName(Object object) {
		return prefix + nextId++ + appendix;
	}

	private static class Handle {
		private Object object;
		public Handle(Object object) {
			this.object = object;
		}

		public boolean equals(Object that) {
			return hashCode() == that.hashCode();
		}
		public int hashCode() {
			int code = System.identityHashCode(object);
			return code;
		}

		public String toString() {
			return "" + hashCode();
		}
	}

	/**
	  * This returns the human readable translation of the given key.
	  **/
	public String nameOf(Object object) {
		Handle handle = new Handle(object);
		String name = (String) table.get(handle);
		if (name == null)
			table.put(handle, name = newName(object));

		return name;
	}


	public boolean contains(Object object) {
		return table.containsKey(new Handle(object));
	}


	public void clear() {
		table.clear();
	}


	public boolean containsKey(Object object) {
		return contains(object);
	}

	public void put(Object object) {
		Handle handle = new Handle(object);
		if (!table.contains(handle)) {
			table.put(handle, newName(object));
		}
	}


	public String toString() {
		return table.toString();
	}

	public static void main(String[] args) {
		NameMap map = new NameMap("ref-");

		String string1 = "111";

		if (map.nameOf(string1).equals("ref-1"))
			System.out.println("passed");
		else
			System.out.println("failed");

		if (map.nameOf("2222").equals("ref-2"))
			System.out.println("passed");
		else
			System.out.println("failed");

		if (map.nameOf(string1).equals("ref-1"))
			System.out.println("passed");
		else
			System.out.println("failed");

		if (map.nameOf("33333").equals("ref-3"))
			System.out.println("passed");
		else
			System.out.println("failed");

		if (map.contains(string1))
			System.out.println("passed");
		else
			System.out.println("failed");
	}
}
