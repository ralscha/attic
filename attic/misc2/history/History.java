

import java.util.*;

public class History implements java.io.Serializable {

	static final int DEFAULT_SIZE_MIN = 5;
	static final int DEFAULT_SIZE_MAX = 10;

	static private Hashtable historyTable; // collection of history

	private Object id; // id of history
	protected TreeSet collection; //	collection of HistoryElement
	private int sizeMin;
	private int sizeMax;

	public History(Object id) {
		this(id, DEFAULT_SIZE_MIN, DEFAULT_SIZE_MAX);
	}

	//	constructor
	public History(Object id, int sizeMin, int sizeMax) {

		this.id = id;
		this.sizeMin = sizeMax;
		this.sizeMax = sizeMax;

		UniversalComparator uc = new UniversalComparator("getObject");
		this.collection = new TreeSet(uc);
	}

	// table related method
	public static synchronized void setHistoryTable(Hashtable table) {
		if (historyTable == null) { //	singleton
			historyTable = table;
		}
	}
	public static Hashtable getHistoryTable() {
		return historyTable;
	}

	public synchronized static History getHistory(Object id) {

		History history = null;

		//get historyString from table
		if (getHistoryTable() != null) {
			history = (History) getHistoryTable().get(id);
		} else {
			if (getHistoryTable() == null) {
				//call setHistoryTable() before to create HistoryObject;
				throw new HistoryTableNotSetException("Call History#setHistoryTable() first.");
			}
		}

		return history;
	}

	public synchronized static void registrateNewHistory(History history) {
		historyTable.put(history.getId(), history);
	}

	//getter/setter
	public Object getId() {
		return id;
	}
	public synchronized void setId(Object id) {
		if (this.id == null) {
			this.id = id;
		}
	}

	public int getSizeMin() {
		return sizeMin;
	}
	public synchronized void setSizeMin(int sizeMin) {
		this.sizeMin = sizeMin;
		removeOld(); // if size decrease,
	}

	public int getSizeMax() {
		return sizeMax;
	}
	public synchronized void setSizeMax(int sizeMax) {
		this.sizeMax = sizeMax;
		removeOld(); // if size decrease,
	}

	public synchronized void add(Object obj) {

		// add history

		//	remove and add( means this element is to set latest in collection)

		HistoryElement element = new HistoryElement(obj);
		if (collection.contains(element)) {
			collection.remove(element);
		}
		removeOld();
		collection.add(element);
	}

	public ArrayList getMatched(Object obj) {
		return null;
	}

	public synchronized void removeOld() {

		// Remove old history
		if (collection.size() >= getSizeMax()) {

			// sort by date

			//	convert TreeSet to Array
			Iterator enu = collection.iterator();
			ArrayList array = new ArrayList();
			while (enu.hasNext()) {
				array.add(enu.next());
			}

			Collections.sort(array, new UniversalComparator("getLastDate"));

			// remove old
			for (int i = 0; i < getSizeMin(); i++) {
				HistoryElement oldHistoryElement = (HistoryElement) array.get(i);
				collection.remove(oldHistoryElement);
			}
		}
	}

	public void clear() {
		collection.clear();
	}

	public Object[] getHistoryElement() {
		//	convert OrderdSet to Array
		Iterator enu = collection.iterator();
		ArrayList array = new ArrayList();
		while (enu.hasNext()) {
			array.add(enu.next());
		}

		ArrayList removed = removeDateInfo(array);

		Object[] history = new Object[removed.size()];
		for (int i = 0; i < history.length; i++) {
			history[i] = removed.get(i);
		}
		return history;
	}

	private ArrayList removeDateInfo(ArrayList array) {

		// remove date information from HistoryElement

		ArrayList extract = new ArrayList();

		for (int i = 0; i < array.size(); i++) {
			HistoryElement element = (HistoryElement) array.get(i);
			extract.add(element.getObject());
		}
		return extract;
	}
}