
/**
 * Sample code by Mark Johnson, JavaWorld, April 2000.
 * Code is may be used for any legal purpose, including commercial
 * purposes, with no warranty expressed or implied.
 * email: mark.johnson@javaworld.com
 */

import org.xml.sax.*;
import java.io.*;

public class RecipeWriter {
	private String _sName = "";
	private String _sQty = null;
	private boolean _isOptional = false;
	private boolean _isVegetarian = true;
	private PrintWriter _pwOut = null;
/**
 * ShoppingListWriter constructor comment.
 */
public RecipeWriter() {
	super();
}
/**
 * ShoppingListWriter constructor comment.
 */
public RecipeWriter(String sOutfile) {
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
 * End description text style
 */
public void endDescription() {
	textEnd();
}
/**
 * End document
 */
public void endIngredients() {
	textEnd();
}
/**
 * Terminate instruction list.
 */
public void endInstructions() {
	println("</OL>");
	textEnd();
}
/**
 * Finish up recipe HTML
 */
public void endRecipe() {
	println("</TABLE></BODY></HTML>");

	println("<P><P><UL>");
	if (_isVegetarian) {
		println("<img src=\"Cow.gif\" ALIGN=\"LEFT\"><font color=\"green\"><P><P><B>This recipe <i>is</i> vegetarian</B></font>");
	} else {
		println("<img src=\"Allig8r.gif\" ALIGN=\"LEFT\"><font color=\"red\"><P><P><B>This recipe <i>is not</i> vegetarian</B></font>");
	}
	println("</UL>");

	if (_pwOut != null)
		_pwOut.close();
}
/**
 * Finish up a step.
 */
public void endStep() {
	println("</LI>");
}
/*
 * Setup title color and size
 */
public void headerPrint(String sHeader) {
	println("<TR VALIGN=\"middle\">" + 
	"<TD BACKGROUND=\"redpaper.jpg\" COLSPAN=\"2\" HEIGHT=\"40\">" +
	"<FONT COLOR=\"#EFD7BF\" SIZE=\"+2\"><B>&nbsp;");
	println(sHeader);
	println("</B></font></TD></TR>");
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
 * Print description header and start text style
 */
public void startDescription() {
	headerPrint("Description");
	textStart();
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
	headerPrint("Ingredients");
	textStart();
}
/**
 * Set up instruction list
 */
public void startInstructions() {
	headerPrint("Instructions");
	textStart();
	println("<OL>");
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
 * Print a step
 */
public void startStep() {
	print("<LI>");
}
/**
 * End a block of text
 */
public void textEnd() {
	println("</B></FONT></TD></TR>");
	println("<TR><TD COLSPAN=\"2\" HEIGHT=\"25\">&nbsp;</TD></TR>");
}
/**
 * Print descriptive text for recipe
 */
public void textOfDescription(String sDesc) {
	print(HtmlEncoder.sEncode(sDesc));
}
/**
 * Report the current ingredient item.
 */
public void textOfItem(String sText) {	
	sText = sText.trim();
	if (_sQty != null) {
		print(HtmlEncoder.sEncode(_sQty) + " ");
	}
	print(HtmlEncoder.sEncode(sText));
	if (_isOptional) {
		println("&nbsp;<i>(optional)</i><br>");
	} else {
		println("<br>");
	}
	_sQty = null;
}
/**
 * Print title for recipe
 */
public void textOfName(String sName) {
	titlePrint(sName);
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
/**
 * Print the text for a recipe step.
 * @param sText java.lang.String
 */
public void textOfStep(String sText) {
	print(HtmlEncoder.sEncode(sText));
}
/**
 * End a block of text
 */
public void textStart() {
	println("<TR><TD WIDTH=\"25\">&nbsp;</TD><TD><FONT COLOR=\"#804040\"><B>");
}
/*
 * Setup title color and size
 */
public void titlePrint(String sTitle) {
	println("<HTML><HEAD><TITLE>" + sTitle + "</TITLE>");
	println("<BODY BACKGROUND=\"creampaper.jpg\">");
	println("<CENTER><FONT COLOR=\"#804040\" SIZE=\"+3\"><B><I>");
	println(sTitle);
	println("</I></B></FONT></CENTER>");
	println("<TABLE BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\" WIDTH=\"100%\">");
}
}