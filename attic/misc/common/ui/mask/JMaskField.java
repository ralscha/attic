package common.ui.mask;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JMaskField extends JTextField implements DocumentListener, KeyListener,
FocusListener {
	protected MaskMacros macros;
	protected MaskDocument doc;
	protected boolean bspace;
	protected boolean delete;
	protected int pos = -1;

	public JMaskField(String mask) {
		this(mask, new MaskMacros(), '_');
	}

	public JMaskField(String mask, MaskMacros macros) {
		this(mask, macros, '_');
	}

	public JMaskField(String mask, MaskMacros macros, char templateChar) {
		setMacros(macros);
		doc = new MaskDocument(mask, macros, templateChar);
		doc.addDocumentListener(this);
		addFocusListener(this);
		addKeyListener(this);
		setDocument(doc);
		setText("");
		setPreferredSize(new Dimension(128, 23));
	}

	public MaskMacros getMacros() {
		return macros;
	}

	public void setMacros(MaskMacros macros) {
		this.macros = macros;
	}

	private void adjustCaretForward(int pos) {
		while (isLiteral(pos)) {
			pos++;
		}
		if (pos > doc.getLength())
			pos = doc.getLength();
		setCaretPosition(pos);
	}

	private void adjustCaretBackward(int pos) {
		while (isLiteral(pos - 1)) {
			pos--;
		}
		if (pos <= 0) {
			adjustCaretForward(0);
		} else
			setCaretPosition(pos);
	}

	private boolean isLiteral(int pos) {
		if (pos < 0 || pos >= doc.pattern.size())
			return false;
		MaskElement rule = doc.getRule(pos);
		if (rule instanceof MaskLiteral) {
			char literal = (((MaskLiteral) rule).chr);
			return !doc.macros.containsMacro(literal);
		}
		return false;
	}

	public void focusLost(FocusEvent event) {
		pos = getCaretPosition();
	}

	public void focusGained(FocusEvent event) {
		if (pos < 0)
			adjustCaretForward(0);
		else
			setCaretPosition(pos);
	}

	public void keyTyped(KeyEvent event) {}
	public void keyReleased(KeyEvent event) {}
	public void keyPressed(KeyEvent event) {
		bspace = event.getKeyCode() == KeyEvent.VK_BACK_SPACE;
		delete = event.getKeyCode() == KeyEvent.VK_DELETE;
	}

	public void changedUpdate(DocumentEvent event) {}
	public void removeUpdate(DocumentEvent event) {}
	public void insertUpdate(DocumentEvent event) {
		int pos = event.getOffset();
		int len = event.getLength();
		if (bspace) {
			adjustCaretBackward(pos);
		} else if (delete) {
			setCaretPosition(pos);
		} else {
			adjustCaretForward(pos + 1);
		}
	}
}