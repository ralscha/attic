package gtf.ss.util;

import java.util.*;

public final class StringUtils {

	
	private static Map specCharMap = new HashMap();
	
	static {
		specCharMap.put(new Character('À'), new Character('A'));
		specCharMap.put(new Character('Á'), new Character('A'));
		specCharMap.put(new Character('Â'), new Character('A'));
		specCharMap.put(new Character('Ã'), new Character('A'));		
		specCharMap.put(new Character('Ä'), new Character('A'));
		specCharMap.put(new Character('Å'), new Character('A'));
		specCharMap.put(new Character('Æ'), new Character('A'));
		specCharMap.put(new Character('Ç'), new Character('C'));
		specCharMap.put(new Character('È'), new Character('E'));
		specCharMap.put(new Character('É'), new Character('E'));
		specCharMap.put(new Character('Ê'), new Character('E'));
		specCharMap.put(new Character('Ë'), new Character('E'));
		specCharMap.put(new Character('Ì'), new Character('I'));
		specCharMap.put(new Character('Í'), new Character('I'));
		specCharMap.put(new Character('Î'), new Character('I'));
		specCharMap.put(new Character('Ï'), new Character('I'));
		specCharMap.put(new Character('Ñ'), new Character('N'));
		specCharMap.put(new Character('Ò'), new Character('O'));
		specCharMap.put(new Character('Ó'), new Character('O'));
		specCharMap.put(new Character('Ô'), new Character('O'));
		specCharMap.put(new Character('Õ'), new Character('O'));
		specCharMap.put(new Character('Ö'), new Character('O'));
		specCharMap.put(new Character('Ù'), new Character('U'));
		specCharMap.put(new Character('Ú'), new Character('U'));
		specCharMap.put(new Character('Û'), new Character('U'));
		specCharMap.put(new Character('Ü'), new Character('U'));
		specCharMap.put(new Character('ß'), new Character('S'));
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