package common.ui.mask;

import java.awt.Toolkit;
import java.util.Vector;
import java.util.Hashtable;

import javax.swing.text.*;

public class MaskDocument extends PlainDocument {
	protected char templateChar;
	protected MaskMacros macros;
	protected MaskTokenizer tokenizer;
	protected Vector pattern = new Vector();
	protected MaskParser parser = new MaskParser();

	public MaskDocument(String mask, MaskMacros macros, char templateChar) {
		this.templateChar = templateChar;
		this.macros = macros;
		parse(mask);
	}

	public void parse(String text) {
		MaskTokenizer tokenizer = new MaskTokenizer("&|![](){} ", "");
		pattern.removeAllElements();
		tokenizer.tokenize(text);
		while (tokenizer.hasMoreTokens()) {
			parseElement(tokenizer);
		}
	}

	private void parseElement(MaskTokenizer tokenizer) {
		MaskToken next = tokenizer.nextToken();
		if (next.equals('{')) {
			pattern.addElement(parser.parseCondition(tokenizer));
			MaskParser.expect(tokenizer.nextToken(), '}');
		} else {
			String text = next.text;
			for (int i = 0; i < text.length(); i++) {
				pattern.addElement(new MaskLiteral(text.charAt(i)));
			}
		}
	}

	public MaskElement getRule(int index) {
		if (index < pattern.size())
			return (MaskElement) pattern.elementAt(index);
		else
			return null;
	}

	public String template() {
		int length = pattern.size();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(template(i));
		}
		return buffer.toString();
	}

	public char template(int pos) {
		MaskElement rule = getRule(pos);
		if (rule instanceof MaskLiteral) {
			char literal = ((MaskLiteral) rule).chr;
			if (!macros.containsMacro(literal))
				return literal;
		}
		return templateChar;
	}

	public boolean match(int pos, char chr) {
		MaskElement element = getRule(pos);
		
		if (element == null) return false;
		
		if (element instanceof MaskLiteral) {
			char macro = ((MaskLiteral) element).chr;
			if (macros.containsMacro(macro)) {
				return macros.getMacro(macro).match(chr);
			}
		}
		return element.match(chr);
	}

	public void insertString(int pos, String text,
                         	AttributeSet attr) throws BadLocationException {
		int len = text.length();
		if (len == 0)
			return;
		if (len > 1) {
			for (int i = pos; i < len; i++)
				insertString(pos, "" + text.charAt(i), attr);
			return;
		} else {
			if (match(pos, text.charAt(0))) {
				super.remove(pos, 1);
				super.insertString(pos, text, attr);
			} else {
				Toolkit.getDefaultToolkit().beep();
				return;
			}
		}
	}

	public void remove(int pos, int length) throws BadLocationException {
		if (length > 1) {
			for (int i = pos; i < length; i++)
				remove(pos, 1);
			return;
		} else {
			if (length == 0 && getLength() == 0) {
				String template = template();
				super.insertString(pos, template, null);
				return;
			}
			if (pos == getLength())
				return;

			String text = "" + template(pos);
			super.remove(pos, 1);
			super.insertString(pos, text, null);
		}
	}

}