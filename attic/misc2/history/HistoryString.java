

import java.util.*;

public class HistoryString extends History implements java.io.Serializable {

	public HistoryString(Object id) {
		super(id, DEFAULT_SIZE_MIN, DEFAULT_SIZE_MAX);
	}

	public HistoryString(Object id, int minSize, int maxSize) {
		super(id, minSize, maxSize);
	}

	public ArrayList getMatched(Object obj) {

		ArrayList lexMatched = new ArrayList();

		HistoryElement master = new HistoryElement(obj);
		SortedSet tailSet = collection.tailSet(master);
		Iterator enu = tailSet.iterator();

		boolean finish = false;
		String masterString = (String) master.getObject();

		while (!finish && enu.hasNext()) {
			HistoryElement element = (HistoryElement) enu.next();
			String elementString = (String) element.getObject();
			if (elementString.startsWith(masterString)) {
				lexMatched.add(elementString);
			} else {
				finish = true;
			}
		}

		return lexMatched;
	}
}