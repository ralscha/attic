package common.ui.mask;

public class MaskExpression implements MaskElement {
	protected MaskElement element;

	public MaskExpression(MaskElement element) {
		this.element = element;
	}

	public String toString() {
		return "expression(" + element.toString() + ")";
	}

	public boolean match(char chr) {
		return element.match(chr);
	}
}