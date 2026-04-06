package ch.rasc.autovalue;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class User {
	static Builder builder() {
		return new AutoValue_User.Builder();
	}

	abstract long loginId();

	abstract String name();

	@AutoValue.Builder
	abstract static class Builder {
		abstract Builder loginId(long l);

		abstract Builder name(String s);

		abstract User autoBuild();

		User build() {
			User u = autoBuild();
			if (u.loginId() < 0) {
				throw new IllegalStateException("Negative loginId");
			}
			return u;
		}
	}
}
