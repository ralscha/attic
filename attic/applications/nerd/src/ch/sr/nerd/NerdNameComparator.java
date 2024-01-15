

package ch.sr.nerd;
import java.util.Comparator;

public class NerdNameComparator implements Comparator {

	public int compare(Object nerdObject1, Object nerdObject2) {

		Nerd nerd1;
		Nerd nerd2;
		
		try {
			nerd1 = (Nerd)nerdObject1;
			nerd2 = (Nerd)nerdObject2;
	
			return(nerd1.getName().compareTo(nerd2.getName()));
					
		} catch (ClassCastException cce) {
			return 0;
		}
	}

}