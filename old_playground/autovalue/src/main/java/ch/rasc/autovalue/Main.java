package ch.rasc.autovalue;

public class Main {
	public static void main(String[] args) {
		Animal dog = Animal.create("dog", 4);

		System.out.println(dog.name());
		System.out.println(dog.numberOfLegs());
		System.out.println(dog.toString());

		User user = User.builder().loginId(1).name("Ralph").build();
		System.out.println(user);

		user = User.builder().loginId(-1).name("John").build();
	}
}
