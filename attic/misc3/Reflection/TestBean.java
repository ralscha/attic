
public class TestBean {

	private String name;
	private String[] country;
	private boolean male;

	public TestBean() {
		name = null;
		country = null;
		male = false;
	}
	
	public String getName() {
		return name;
	}

	public String[] getCountry() {
		return country;
	}

	public boolean isMale() {
		return male;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCountry(String[] country) {
		this.country = country;
	}

	public void setMale(boolean male) {
		this.male = male;
	}	

}