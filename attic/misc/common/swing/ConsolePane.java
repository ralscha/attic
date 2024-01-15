package common.swing;

import java.io.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.text.*;


/**
 * A display area for console output.  This component
 * will display the output of a PrintStream, PrintWriter,
 * or of a running process in a swing text component.
 * The text from the output and error pipes to the child
 * process can be displayed with whatever character
 * attributes desired.
 *
 * @author  Timothy Prinzing
 * @version 1.2 03/04/99
 */
public class ConsolePane extends JScrollPane {

	/**
	 * Create a console display.  By default
	 * the text region is set to not be editable.
	 */
	public ConsolePane() {
		super();
		outputArea = createOutputArea();
		outputArea.setEditable(false);
		JViewport vp = getViewport();
		vp.add(outputArea);
		//vp.setBackingStoreEnabled(true);
	}

	/**
	  * Create the component to be used to display the
	  * process output.  This is a hook to allow the
	  * component used to be customized.
	  */
	protected JTextComponent createOutputArea() {
		JTextPane pane = new JTextPane();
		return pane;
	}

	public void clear() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Document doc = outputArea.getDocument();
				try {
					doc.remove(0, doc.getLength());
				} catch (javax.swing.text.BadLocationException ble) {
					System.err.println(ble);
				}
			}});
	}

	public void scrollToEnd() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Document doc = outputArea.getDocument();
				outputArea.setCaretPosition(doc.getEndPosition().getOffset()-1);
			}});
	}
		

	/**
	  * Create a PrintStream that will display in the console
	  * using the given attributes.
	  */
	public PrintStream createPrintStream(AttributeSet a) {
		Document doc = outputArea.getDocument();
		OutputStream out = new DocumentOutputStream(doc, a);
		PrintStream pOut = new PrintStream(out);
		return pOut;
	}
	
	public PrintStream createPrintStream() {
		Document doc = outputArea.getDocument();
		OutputStream out = new DocumentOutputStream(doc);
		PrintStream pOut = new PrintStream(out);
		return pOut;
	}
	

	/**
	  * Create a PrintWriter that will display in the console
	  * using the given attributes.
	  */
	public PrintWriter createPrintWriter(AttributeSet a) {
		Document doc = outputArea.getDocument();
		Writer out = new DocumentWriter(doc, a);
		PrintWriter pOut = new PrintWriter(out);
		return pOut;
	}

	public PrintWriter createPrintWriter() {
		Document doc = outputArea.getDocument();
		Writer out = new DocumentWriter(doc);
		PrintWriter pOut = new PrintWriter(out);
		return pOut;
	}


	/**
	  * Fetch the component used for the output.  This
	  * allows further parsing of the output if desired,
	  * and allows things like mouse listeners to be
	  * attached.  This can be useful for things like
	  * compiler output where clicking on an error
	  * warps another view to the location of the error.
	  */
	public JTextComponent getOutputArea() {
		return outputArea;
	}

	/**
	  * Set the attributes to use when displaying the output
	  * pipe to the process being monitored.
	  */
	public void setOutputAttributes(AttributeSet a) {
		outputAttr = a.copyAttributes();
	}

	/**
	  * Get the attributes being used when displaying the output
	  * pipe to the process being monitored.
	  */
	public AttributeSet getOutputAttributes() {
		return outputAttr;
	}

	/**
	  * Set the attributes to use when displaying the error
	  * pipe to the process being monitored.
	  */
	public void setErrorAttributes(AttributeSet a) {
		errorAttr = a.copyAttributes();
	}

	/**
	  * Get the attributes being used when displaying the error
	  * pipe to the process being monitored.
	  */
	public AttributeSet getErrorAttributes() {
		return errorAttr;
	}

	

	/**
	  * Display an error into the output area.  By default
	  * this displays the given string with a foreground color
	  * of Color.red.  This is used if an exception is thrown
	  * when monitoring a running process.
	  */
	public void error(String s) {
		Document doc = outputArea.getDocument();
		MutableAttributeSet a = new SimpleAttributeSet();
		StyleConstants.setForeground(a, Color.red);
		try {
			doc.insertString(doc.getLength(), s, a);
		} catch (Throwable e) {
			getToolkit().beep();
		}
	}

	private JTextComponent outputArea;
	private AttributeSet outputAttr;
	private AttributeSet errorAttr;

	

}