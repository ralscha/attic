
import com.sun.kjava.*;

public class Mice extends Spotlet {

	public Mice() {
		new MiceCanvas(10, 15).start();
	}

	public static void main(String[] args) {	
		new Mice().register(NO_EVENT_OPTIONS);
	}

}