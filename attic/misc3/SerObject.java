

public class SerObject implements java.io.Serializable {

	private String ref1;
	private String ref2;
	private int i;

	public SerObject(int i) {
		ref1 = "Referenz1";
		ref2 = "Referenz2";
		this.i = i;
	}

	public String toString() {
		return ref1 + " " + ref2 + " " + i;
	}

}