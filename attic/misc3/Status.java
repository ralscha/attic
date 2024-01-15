

public class Status implements java.io.Serializable  {

	private String message;
	private int no;
	
	public Status() {
		this(null, -1);
	}
	
	public Status(String message, int no) {
		this.message = message;
		this.no = no;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}