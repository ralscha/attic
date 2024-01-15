
public class CountPortableImpl extends CounterPortable._CountImplBase {
	private int sum;

    public CountPortableImpl() {
        super();
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
