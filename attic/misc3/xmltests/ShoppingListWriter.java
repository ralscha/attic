
/**
 * Sample code by Mark Johnson, JavaWorld, April 2000.
 * Code is may be used for any legal purpose, including commercial
 * purposes, with no warranty expressed or implied.
 * email: mark.johnson@javaworld.com
 */

import org.xml.sax.*;
import java.io.*;

public class ShoppingListWriter {
	private String _sName = "";
	private String _sQty = null;
	private boolean _isOptional = false;
	private boolean _isVegetarian = true;
	private PrintWriter _pwOut = null;
/**
 * ShoppingListWriter constructor comment.
 */
public ShoppingListWriter() {
	super();
}
/**
 * ShoppingListWriter constructor comment.
 */
public ShoppingListWriter(String sOutfile) {
	super();

	File fOutfile = new File(sOutfile);
	sOutfile = fOutfile.getAbsolutePath();
		
	try {
		_pwOut = new PrintWriter(new FileWriter(sOutfile));
	} catch (IOException ex) {
		System.err.println("IO error: " + ex);
		System.err.println("Writing to standard output");
	}
}
/**
 * End document
 */
public void endIngredients() {
	println("</B></TD></TR>");
	println("</TABLE>");
	
	println("</BODY></HTML>");
	if (_pwOut != null) {
		_pwOut.close();
	}
}
/**
 * Print a string to the current output stream.
 * @param s java.lang.String
 */
public void print(String s) {
	if (_pwOut == null) {
		System.out.print(s);
	} else {
		_pwOut.print(s);
	}
}
/**
 * Print a string to the current output stream.
 * @param s java.lang.String
 */
public void println(String s) {
	if (_pwOut == null) {
		System.out.println(s);
	} else {
		_pwOut.println(s);
	}
}
/**
 * Detect the presence of a nonvegetarian ingredient.
 */
public void startIngredient(AttributeList al) {
	String sVegetarian = al.getValue("vegetarian");
	if (sVegetarian != null && (sVegetarian.equals("0") || sVegetarian.equals("false"))) {
		_isVegetarian = false;
	}
}
/**
 * Print title for shopping list
 */
public void startIngredients() {
	println("<HTML><HEAD><TITLE>");

	println("Shopping list");
	if (_sName != null) {
		println(" for " + _sName);
	}
	println("</TITLE></HEAD>");
	println("<BODY BACKGROUND=\"spiral.gif\">");
	println("<H3>");

	println("<TABLE BORDER=\"0\">");
	println("<TR><TD>&nbsp;</TD><TD COLSPAN=\"2\"><H3>");
	println("Shopping list");
	if (_sName != null) {
		println(" for <i>" + _sName + "</i>");
	}
	println("</H3></TD></TR>");
	println("<TR><TH WIDTH=\"100\"></TH><TH ALIGN=\"LEFT\">Quantity</TH>"+
		"<TH ALIGN=\"LEFT\">Ingredient</TH></TR>");
}
/**
 * Records whether the item is optional.
 * @param alAttrs org.xml.sax.AttributeList
 */
public void startItem(AttributeList alAttrs) {
	String sOptional = alAttrs.getValue("optional");
	if (sOptional != null && (sOptional.equals("true") || sOptional.equals("1"))) {
		_isOptional = true;
	} else {
		_isOptional = false;
	}
}
/**
 * Save the "units" attribute for the current quantity.
 */
public void startQty(AttributeList al) {
	String sUnits = al.getValue("unit");

	if (sUnits != null) {
		_sQty = sUnits;
	}
}
/**
 * Report the current ingredient item.
 */
public void textOfItem(String sText) {
	String sS = (_isOptional ? "<font color=\"red\">" : "");
	String sE = (_isOptional ? "</font>" : "");
	
	sText = sText.trim();
	if (_sQty == null) {
		_sQty = "&nbsp;";
	}
	print("<TR><TD>&nbsp;</TD><TD>" + sS + _sQty + sE);
	println("</TD><TD>" + sS + sText + sE +"</TD></TR>");
	_sQty = null;
}
/**
 * Save the name of the recipe for later printing.
 */
public void textOfName(String sText) {
	_sName = sText;
}
/**
 * Save the ingredient quantity for later reporting. If it's empty,
 * the entire ingredient is empty.
 */
public void textOfQty(String sText) {
	sText = sText.trim();
	if (sText.length() > 0) {
		if (_sQty == null) {
			_sQty = sText;
		} else {
			_sQty = sText + " " + _sQty;
		}
	} else {
		_sQty = "&nbsp;";
	}
}
}