package wampclient;

public class ReasonCode {
	public String category;
	public String code;
	public String label;

	@Override
	public String toString() {
		return "ReasonCode [category=" + this.category + ", code=" + this.code
				+ ", label=" + this.label + "]";
	}

}
