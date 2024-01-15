package common.ui.mask;

public class MaskToken {
	protected int pos;
	protected String text;

	public MaskToken(int pos, String text) {
		this.pos = pos;
		this.text = text;
	}

	public boolean equals(char chr) {
		if (text.length() == 1)
			return chr == text.charAt(0);
		else
			return false;
	}

	public boolean equals(String test) {
		return text.equals(test);
	}

	public String toString() {
		return "token(" + pos + "," + '"' + text + '"' + ")";
	}
}