public class RefNo {

	private int refno;

	public RefNo(int refno) {
		this.refno = refno;
	}

	public int getRefno() {
		return refno;
	}

	public void setRefno(int refno) {
		this.refno = refno;
	}

	public void incRefno() {
		refno++;
	}
	
	public String toString() {
		return "RefNo("+ refno+")";
	}
}
