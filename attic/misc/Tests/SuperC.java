
public class SuperC {
	
	
	static {
		System.out.println("SuperC Static Initializer");	
	}
	
	{
		System.out.println("SuperC Instance Initializer");
	}
	
	public SuperC() {}
	
	public SuperC(int i) {
		System.out.println("SuperC Constructor");
	}
	
	

}