

package ch.sr.nerd;
import java.util.Comparator;

public class NerdPointsComparator implements Comparator {

	public int compare(Object nerdObject1, Object nerdObject2) {

		Nerd nerd1;
		Nerd nerd2;
		
		try {
			nerd1 = (Nerd)nerdObject1;
			nerd2 = (Nerd)nerdObject2;
	
			int i = nerd2.getPoints() - nerd1.getPoints();
			if (i < 0) 
				return -1;
			else if (i > 0) 
				return 1;
			else 
				return 0;
					
		} catch (ClassCastException cce) {
			return 0;
		}
	}

}