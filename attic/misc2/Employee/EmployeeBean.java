
public class EmployeeBean {
	private int id;
	private String firstName;
	private String lastName;
	private String image;
	private String email;
	private String department;

	public EmployeeBean(int id) {
		this.id = id;
		firstName = "";
		lastName = "";
		image = "";
		email = "";
		department = "";
	}

	public EmployeeBean() {
		this(0);
	}

	public int getId() {
		return this.id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return this.image;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartment() {
		return this.department;
	}
}
