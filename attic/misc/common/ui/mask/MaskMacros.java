package common.ui.mask;

import java.util.Hashtable;
import java.util.Enumeration;

public class MaskMacros {
	protected Hashtable table;
	protected MaskParser parser = new MaskParser();

	public MaskMacros() {
		table = new Hashtable();
	}

	public void addMacro(char key, String macro) {
		MaskElement element = parser.parseMacro(macro);
		table.put(new Character(key), element);
	}

	public void removeMacro(char key) {
		table.remove(new Character(key));
	}

	public MaskElement getMacro(char key) {
		return (MaskElement) table.get(new Character(key));
	}

	public boolean containsMacro(char key) {
		return table.containsKey(new Character(key));
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("macros\n{\n");
		Enumeration keys = table.keys();
		Enumeration enum = table.elements();
		Character key;
		MaskElement element;
		while (keys.hasMoreElements()) {
			key = (Character) keys.nextElement();
			element = (MaskElement) enum.nextElement();
			buffer.append(" " + key.charValue() + "=");
			buffer.append(element.toString() + "\n");
		}
		buffer.append("}\n");
		return buffer.toString();
	}

	public static void main(String[] args) {
		MaskMacros macros = new MaskMacros();
		macros.addMacro('@', "[a-zA-Z]");
		macros.addMacro('#', "[0-9]");
		System.out.println(macros);
	}
}