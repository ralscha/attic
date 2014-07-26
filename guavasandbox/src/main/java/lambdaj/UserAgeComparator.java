package lambdaj;

public class UserAgeComparator implements java.util.Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		return o1.getAge() - o2.getAge();
	}

}
