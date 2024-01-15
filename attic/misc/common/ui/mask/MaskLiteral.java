package common.ui.mask;

public class MaskLiteral implements MaskElement {
	protected char chr;

	public MaskLiteral(char chr) {
		this.chr = chr;
	}

	public String toString() {
		return "literal('" + chr + "')";
	}

	public boolean match(char chr) {
		return this.chr == chr;
	}
}