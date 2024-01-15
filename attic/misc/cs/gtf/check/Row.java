package gtf.check;

public class Row implements java.io.Serializable {

	public String account;
	public String bal1;
	public String bal2;
	public String diff;
	
	public Row(String a, String b1, String b2, String d) {
		this.account = a;
		this.bal1 = b1;
		this.bal2 = b2;
		this.diff = d;
	}
	
	public boolean equals(Row other) {
		return ( account.equals(other.account) &
		         bal1.equals(other.bal1) &
					bal2.equals(other.bal2) &
					diff.equals(other.diff) );
	}

}