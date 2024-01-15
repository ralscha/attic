package common.ui.mask;

import java.util.Vector;

public class MaskTokenizer {
	protected Vector tokens = new Vector();
	protected String include, exclude;
	protected int pos = 0;

	public MaskTokenizer(String include, String exclude) {
		this.include = include;
		this.exclude = exclude;
	}

	public void tokenize(String text) {
		int prev = 0;
		tokens.removeAllElements();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			if (include.indexOf(text.charAt(i)) > -1) {
				if (buffer.length() > 0)
					tokens.addElement(new MaskToken(prev, buffer.toString()));
				tokens.addElement(new MaskToken(i, "" + text.charAt(i)));
				buffer.setLength(0);
				prev = i + 1;
			} else if (exclude.indexOf(text.charAt(i)) > -1) {
				if (buffer.length() > 0)
					tokens.addElement(new MaskToken(prev, buffer.toString()));
				buffer.setLength(0);
				prev = i + 1;
			} else
				buffer.append(text.charAt(i));
		}
		if (buffer.length() > 0)
			tokens.addElement(new MaskToken(prev, buffer.toString()));
	}

	public boolean hasMoreTokens() {
		return pos < tokens.size();
	}

	public MaskToken nextToken() {
		return (MaskToken) tokens.elementAt(pos++);
	}

	public void ignoreToken() {
		if (pos > 0)
			pos--;
	}

	public static void main(String[] args) {
		MaskTokenizer tokenizer = new MaskTokenizer("-,", " \t\n");
		String text = "123-45-6--7,234  2423";
		tokenizer.tokenize(text);
		System.out.println(text);
		//System.out.println(tokenizer);
		while (tokenizer.hasMoreTokens()) {
			System.out.println(tokenizer.nextToken());
		}
	}
}