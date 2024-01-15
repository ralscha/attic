public class SuperSingleton {
	private static SuperSingleton instance = new SuperSingleton();
	protected int x;

	protected SuperSingleton() {
		x = 0;
	}

	public static SuperSingleton instance() {
		return instance;
	}

	public void inc() {
		x++;
	}

	public void mult() {
		throw new UnsupportedOperationException();
	}

	public void printX() {
		System.out.println(x);
	}
}