import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.annotations.Trim;

public class User {

	@Trim
	@Parsed(field = "Name")
	private String name;

	@Trim
	@Parsed(field = "E-Mail")
	private String email;

	@Parsed(field = "ID")
	private long id;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [name=" + this.name + ", email=" + this.email + ", id=" + this.id
				+ "]";
	}

}
