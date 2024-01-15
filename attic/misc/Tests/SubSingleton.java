public class SubSingleton extends SuperSingleton {
	private static SubSingleton instance = new SubSingleton();

	protected SubSingleton() {
		super();
	}

	public static SuperSingleton instance() {
		return instance;
	}

	public void inc() {
		x += 2;
	}
	
	public void mult() {
		x = 2*x;
	}
}