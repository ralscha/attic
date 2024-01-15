
public class MemoryTest {

	public void go() {
    //TestRunner tr = new TestRunner();
		while(true) {
      new TestRunner().checkReminders();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ie) {
				return;
			}
		}
	}

	public static void main(String[] args) {

    try {
	    Class.forName("com.codestudio.sql.PoolMan").newInstance();
	  } catch (Exception ex) {
	    System.out.println("Could Not Find the PoolMan Driver. " +
		         "Is PoolMan.jar in your CLASSPATH?");
	    System.exit(0);
	  }

		new MemoryTest().go();
	}

}