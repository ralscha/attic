package gtf.ss.util;

import java.util.*;

public final class StringUtils {

	
	private static Map specCharMap = new HashMap();
	
	static {
		specCharMap.put(new Character('�'), new Character('A'));
		specCharMap.put(new Character('�'), new Character('A'));
		specCharMap.put(new Character('�'), new Character('A'));
		specCharMap.put(new Character('�'), new Character('A'));		
		specCharMap.put(new Character('�'), new Character('A'));
		specCharMap.put(new Character('�'), new Character('A'));
		specCharMap.put(new Character('�'), new Character('A'));
		specCharMap.put(new Character('�'), new Character('C'));
		specCharMap.put(new Character('�'), new Character('E'));
		specCharMap.put(new Character('�'), new Character('E'));
		specCharMap.put(new Character('�'), new Character('E'));
		specCharMap.put(new Character('�'), new Character('E'));
		specCharMap.put(new Character('�'), new Character('I'));
		specCharMap.put(new Character('�'), new Character('I'));
		specCharMap.put(new Character('�'), new Character('I'));
		specCharMap.put(new Character('�'), new Character('I'));
		specCharMap.put(new Character('�'), new Character('N'));
		specCharMap.put(new Character('�'), new Character('O'));
		specCharMap.put(new Character('�'), new Character('O'));
		specCharMap.put(new Character('�'), new Character('O'));
		specCharMap.put(new Character('�'), new Character('O'));
		specCharMap.put(new Character('�'), new Character('O'));
		specCharMap.put(new Character('�'), new Character('U'));
		specCharMap.put(new Character('�'), new Character('U'));
		specCharMap.put(new Character('�'), new Character('U'));
		specCharMap.put(new Character('�'), new Character('U'));
		specCharMap.put(new Character('�'), new Character('S'));
	}
	
	public static String createSearchString(String input) {
	
		StringBuffer sb = new StringBuffer(input.length());
		
		for (int i = 0; i < input.length(); i++) {			
			char c = input.charAt(i);
			if (Character.isLetterOrDigit(c)) {
				c = Character.toUpperCase(c);
				c = getSpecChar(c);
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
	
	private static char getSpecChar(char c) {
		Character subst = (Character)specCharMap.get(new Character(c));
		if (subst != null)
			return subst.charValue();
		else	
			return c;
	}
	
}