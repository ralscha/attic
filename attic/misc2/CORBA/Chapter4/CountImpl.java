
public class CountImpl extends Counter._CountImplBase {
	private int sum;

    public CountImpl(String name) {
	    super(name);
    	System.out.println("Count Object Created");
	    sum = 0;  
    }

    public int sum() {
	    return sum;
    }

    public void sum(int val) {
        sum = val;
    }

    public int increment() {
	    sum++;
        return sum;
  }

}
