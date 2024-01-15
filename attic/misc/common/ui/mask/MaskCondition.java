package common.ui.mask;

public class MaskCondition implements MaskElement {
	public static final boolean AND = true;
	public static final boolean OR = false;

	protected boolean and;
	protected MaskElement left, right;

	public MaskCondition(boolean and, MaskElement left, MaskElement right) {
		this.and = and;
		this.left = left;
		this.right = right;
	}

	public String toString() {
		return "rule(" + left.toString() + (and ? " and " : " or ") + right.toString() + ")";
	}

	public boolean match(char chr) {
		if (and)
			return left.match(chr) && right.match(chr);
		else
			return left.match(chr) || right.match(chr);
	}
}