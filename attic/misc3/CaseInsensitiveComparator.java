import java.util.*;

public class CaseInsensitiveComparator implements Comparator {

	public int compare(Object element1, Object element2) {
		String lowerE1 = ((String) element1).toLowerCase();
		String lowerE2 = ((String) element2).toLowerCase();
		return lowerE1.compareTo(lowerE2);
	}
}