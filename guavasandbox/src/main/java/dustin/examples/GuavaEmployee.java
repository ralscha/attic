package dustin.examples;

/**
 * Simple employee class using Guava-powered 'common' methods implementations.
 *
 * I explicitly scope the com.google.common.base.Objects class here to avoid the inherent
 * name collision with the java.util.Objects class.
 *
 * @author Dustin
 */
public class GuavaEmployee {
	public enum Gender {
		FEMALE, MALE
	}

	private final String lastName;

	private final String firstName;

	private final String employerName;

	private final TraditionalEmployee.Gender gender;

	/**
	 * Create an instance of me.
	 *
	 * @param newLastName The new last name my instance will have.
	 * @param newFirstName The new first name my instance will have.
	 * @param newEmployerName The employer name my instance will have.
	 * @param newGender The gender of my instance.
	 */
	public GuavaEmployee(String newLastName, String newFirstName, String newEmployerName,
			final TraditionalEmployee.Gender newGender) {
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

	public TraditionalEmployee.Gender getGender() {
		return this.gender;
	}

	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Using Guava to compare provided object to me for equality.
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
		final GuavaEmployee other = (GuavaEmployee) obj;

		return com.google.common.base.Objects.equal(this.lastName, other.lastName)
				&& com.google.common.base.Objects.equal(this.firstName, other.firstName)
				&& com.google.common.base.Objects.equal(this.employerName,
						other.employerName)
				&& com.google.common.base.Objects.equal(this.gender, other.gender);
	}

	/**
	 * Uses Guava to assist in providing hash code of this employee instance.
	 *
	 * @return My hash code.
	 */
	@Override
	public int hashCode() {
		return com.google.common.base.Objects.hashCode(this.lastName, this.firstName,
				this.employerName, this.gender);
	}

	/**
	 * Method using Guava to provide String representation of this employee instance.
	 *
	 * @return My String representation.
	 */
	@Override
	public String toString() {
		return com.google.common.base.Objects.toStringHelper(this)
				.addValue(this.lastName).addValue(this.firstName)
				.addValue(this.employerName).addValue(this.gender).toString();
	}
}
