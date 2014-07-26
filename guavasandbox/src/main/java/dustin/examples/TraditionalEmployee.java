package dustin.examples;

/**
 * Simple employee class using NetBeans-generated 'common' methods implementations that
 * are typical of many such implementations created without Guava or other library.
 *
 * @author Dustin
 */
public class TraditionalEmployee {
	public enum Gender {
		FEMALE, MALE
	}

	private final String lastName;

	private final String firstName;

	private final String employerName;

	private final Gender gender;

	/**
	 * Create an instance of me.
	 *
	 * @param newLastName The new last name my instance will have.
	 * @param newFirstName The new first name my instance will have.
	 * @param newEmployerName The employer name my instance will have.
	 * @param newGender The gender of my instance.
	 */
	public TraditionalEmployee(String newLastName, String newFirstName,
			String newEmployerName, final Gender newGender) {
		this.lastName = newLastName;
		this.firstName = newFirstName;
		this.employerName = newEmployerName;
		this.gender = newGender;
	}

	public String getEmployerName() {
		return this.employerName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public Gender getGender() {
		return this.gender;
	}

	public String getLastName() {
		return this.lastName;
	}

	/**
	 * NetBeans-generated method that compares provided object to me for equality.
	 *
	 * @param obj Object to be compared to me for equality.
	 * @return {@code true} if provided object is considered equal to me or {@code false}
	 * if provided object is not considered equal to me.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TraditionalEmployee other = (TraditionalEmployee) obj;
		if (this.lastName == null ? other.lastName != null : !this.lastName
				.equals(other.lastName)) {
			return false;
		}
		if (this.firstName == null ? other.firstName != null : !this.firstName
				.equals(other.firstName)) {
			return false;
		}
		if (this.employerName == null ? other.employerName != null : !this.employerName
				.equals(other.employerName)) {
			return false;
		}
		if (this.gender != other.gender) {
			return false;
		}
		return true;
	}

	/**
	 * NetBeans-generated method that provides hash code of this employee instance.
	 *
	 * @return My hash code.
	 */
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 19 * hash + (this.lastName != null ? this.lastName.hashCode() : 0);
		hash = 19 * hash + (this.firstName != null ? this.firstName.hashCode() : 0);
		hash = 19 * hash + (this.employerName != null ? this.employerName.hashCode() : 0);
		hash = 19 * hash + (this.gender != null ? this.gender.hashCode() : 0);
		return hash;
	}

	/**
	 * NetBeans-generated method that provides String representation of employee instance.
	 *
	 * @return My String representation.
	 */
	@Override
	public String toString() {
		return "TraditionalEmployee{" + "lastName=" + lastName + ", firstName="
				+ firstName + ", employerName=" + employerName + ", gender=" + gender
				+ '}';
	}
}
