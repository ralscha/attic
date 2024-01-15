
package address;

public class Address implements java.io.Serializable {
	private String name;
	private String firstName;
	private String street;
	private String city;
	private String tel;
	private String email;
	private String url;
	private String comments;
	private int id;

	public void setName(String name) {
		this.name = name;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setURL(String url) {
		this.url = url;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getTel() {
		return tel;
	}
	public String getEmail() {
		return email;
	}
	public String getURL() {
		return url;
	}
	public String getComments() {
		return comments;
	}
	public int getId() {
		return id;
	}

	public String toString() {
		return getFirstName() + ";"+getName() + ";"+getStreet() + ";"+getCity() + ";"+
       		getTel() + ";"+getEmail() + ";"+getURL() + ";"+getComments();
	}
}

