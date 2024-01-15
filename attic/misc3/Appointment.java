

public class Appointment implements java.io.Serializable {

	private String start;
	private String end;
	private String description;

	public Appointment() {
		this(null, null, null);
	}

	public Appointment(String start, String end, String description) {
		this.start = start;
		this.end = end;
		this.description = description;
	}


	public String getStart()
	{
		return this.start;
	}

	public void setStart(String start)
	{
		this.start = start;
	}


	public String getEnd()
	{
		return this.end;
	}

	public void setEnd(String end)
	{
		this.end = end;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}