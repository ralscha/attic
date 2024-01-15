

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.util.*;


public class JTextFieldHistory extends JTextField implements DocumentListener,
DocumentChangingListener {

	// property
	/* reference to historyString*/
	private HistoryStringReference historyString;

	//	for matching(using internal)

	/**internal use*/
	protected String enteredString;
	/** internal use*/
	protected ArrayList matchedArray;
	/** internal use*/
	protected int pos;

	//	for selection of textfield(using internal)
	/** internal use*/
	protected boolean neglectTextChange;
	/** internal use*/
	protected boolean requiredSelect;
	/** internal use*/
	protected int selectionStart;
	/** internal use*/
	protected int selectionEnd;

	/** default constructor*/
	public JTextFieldHistory() {
		super();
		initialize();
	}

	/** constructor with historyReference id
	
	    @param id of history reference
	  */
	public JTextFieldHistory(Object id) {
		super();
		initialize();
		historyString = new HistoryStringReference(id);
	}

	/**
	   @param  id of history reference
	   @param  minimum size of history
	   @param  maximum size of history
	 */
	public JTextFieldHistory(Object id, int minSize, int maxSize) {
		super();
		initialize();
		historyString = new HistoryStringReference(id, minSize, maxSize);
	}

	/**
	    construct with history reference
	    @param reference object
	  */
	public JTextFieldHistory(HistoryStringReference ref) {
		super();
		initialize();
		historyString = ref;
	}

	//	getter of property
	/**
	  getter method for historyString
	  @return historyString
	*/
	public HistoryStringReference getHistoryString() {
		return historyString;
	}

	/** setter method for historyString
	  @param history reference to set
	  */
	public void setHistoryString(HistoryStringReference history) {
		this.historyString = history;
	}

	/** add history
	 */
	public void addHistory(String str) {

		if (historyString != null) {
			historyString.getHistory().add(str);
		}
		enteredString = "";
		neglectTextChange = false;
	}

	/** remove old history
	 */
	public void removeOld() {
		historyString.getHistory().removeOld();
	}

	/**
	   remove all history
	 */
	public void clear() {
		historyString.getHistory().clear();
	}

	/** return all history
	 @return all history
	 */
	public Object[] getHistory() {
		return historyString.getHistory().getHistoryElement();
	}

	private void initialize() {
		// setup event
		addKeyListener(new JTextFieldHistory_KeyAdapter(this));
		addFocusListener(new JTextFieldHistory_FocusAdapter(this));

		AbstractDocument plaindoc = (AbstractDocument) this.getDocument();
		DocumentCatchChanging doc = new DocumentCatchChanging();
		doc.addDocumentListener(this);
		doc.setDocumentChangingListener(this);
		this.setDocument(doc);

		enteredString = "";
		neglectTextChange = false;
		matchedArray = null;
		pos = 0;

		requiredSelect = false;
		selectionStart = 0;
		selectionEnd = 0;
	}

	/** implementation of DocumentChangingListener
	 */
	public String gointToUpdate(int offs, java.lang.String str, AttributeSet a) {

		// if history function is set to off
		if (historyString == null) {
			return str;
		}

		if (str == null || str.length() == 0) {
			return str;
		}

		if (offs < getText().length()) {
			System.out.println("does not support insert");
			return str;
		}

		if (neglectTextChange) {
			neglectTextChange = false;
			return str;
		}

		this.enteredString = getText() + str;
		String modifiedString = str; // initial value
		matchedArray = historyString.getHistory().getMatched(enteredString);

		if (matchedArray.size() > 0) { //	if there is any history,
			pos = 0;

			//if entered and matched is the same, there is no way to notify matched.
			//entered(abc) matched(abc,abcd)
			if (enteredString.equals((String) matchedArray.get(0)) && matchedArray.size() > 1) {
				pos = 1;
			}

			String fullString = (String) matchedArray.get(pos);
			modifiedString = fullString.substring(offs, fullString.length());

			//System.out.println("offs:"+offs);

			requiredSelect = true;
			neglectTextChange = true;
			this.selectionStart = offs + 1;
			this.selectionEnd = fullString.length();
		}

		return modifiedString;
	}

	void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (matchedArray != null) {
			if (keyCode == KeyEvent.VK_DOWN) {
				if (pos < matchedArray.size() - 1) {
					setMatched(++pos);
					setSelect();
				}
			} else if (keyCode == KeyEvent.VK_UP) {
				if (pos > 0) {
					setMatched(--pos);
					setSelect();
				}
			} else {
				//clear
				matchedArray = null;
				pos = 0;
				neglectTextChange = false;
			}
		}
	}

	private void setSelect() {
		this.requiredSelect = true;
		this.selectionStart = getSelectionStart();
		this.selectionEnd = getSelectionEnd();
	}

	protected void setMatched(int position) {

		String fullString = (String) matchedArray.get(position);

		this.setText(fullString);

		select(enteredString.length(), fullString.length());
		neglectTextChange = true;
	}

	//	focus event
	void focusLost(FocusEvent e) {
		select(0, 0); //deselect
	}
	void focusGained(FocusEvent e) {
		if (getText().length() > 0) {
			selectAll();
		}
	}

	/** You don't need to call this.
	 implementation of DocumentListener
	 */
	public void insertUpdate(DocumentEvent e) {

		//System.out.println("insertUpdate");
		if (requiredSelect) {
			//System.out.println("doSelect from "+selectionStart+" to "+selectionEnd);
			requiredSelect = false;
			select(selectionStart, selectionEnd);
		}
	}

	/**
	   You don't need to call this.
	   Implementation of DocumentListener
	 */
	public void removeUpdate(DocumentEvent e) {
		// do nothing
	}

	/**
	   You don't need to call this.
	   Implementation of DocumentListener
	 */
	public void changedUpdate(DocumentEvent e) {
		// do nothing
	}
}

class JTextFieldHistory_KeyAdapter extends java.awt.event.KeyAdapter {
	JTextFieldHistory adaptee;

	JTextFieldHistory_KeyAdapter(JTextFieldHistory adaptee) {
		this.adaptee = adaptee;
	}

	public void keyPressed(KeyEvent e) {
		adaptee.keyPressed(e);
	}
}

class JTextFieldHistory_FocusAdapter extends java.awt.event.FocusAdapter {
	JTextFieldHistory adaptee;

	JTextFieldHistory_FocusAdapter(JTextFieldHistory adaptee) {
		this.adaptee = adaptee;
	}

	public void focusLost(FocusEvent e) {
		adaptee.focusLost(e);
	}

	public void focusGained(FocusEvent e) {
		adaptee.focusGained(e);
	}
}