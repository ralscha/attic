package ch.rasc.bytebuddy;

public class TestService {

	@Secured(requiredUser = "admin")
	public void securedMethod() {
		System.out.println("secured");
	}

	@Secured(requiredUser = "user")
	public void securedUserMethod() {
		System.out.println("secured user");
	}

	public void publicMethod() {
		System.out.println("public");
	}
}
