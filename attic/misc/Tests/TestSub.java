

class Base {
	public int x = 2;
	
	public int getX() {
		return x;
	}
}

class Sub extends Base {
	public int x = 3;
	
	public int getX() {
		return x;
	}
}

public class TestSub {
	public static void main(String[] args) {
		Base b = new Sub();
		System.out.println(b.x);
		System.out.println(b.getX());
	}
}

