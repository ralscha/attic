
public class SWIFTService {

	private static Latch latch = new Latch();
	public static void main(String[] args) {
	    new SWIFTServer();	    
		latch.waitForRelease();
	}		

	public static void shutdown() {
		latch.release();
	}

}

class Latch  {
	private boolean _released;

	public Latch()  {
		_released = false;
	}
	
	public synchronized void waitForRelease()  {
		while (!_released)  {
			try  {
				wait();
			} catch (InterruptedException ex)  { }
		}
	}

	public synchronized void release()  {
		_released = true;
		notifyAll();
	}
}
