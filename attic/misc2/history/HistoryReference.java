
public abstract class HistoryReference implements java.io.Serializable {

	//point historyObject
	private History history;

	//	property of bean
	private Object id;
	private int sizeMin;
	private int sizeMax;

	public HistoryReference() { }
	
	public HistoryReference(Object id) {
		instantiate(id);
		setSizeMin(History.DEFAULT_SIZE_MIN);
		setSizeMax(History.DEFAULT_SIZE_MAX);
	}

	public HistoryReference(Object id, int sizeMin, int sizeMax) {
		instantiate(id);
		setSizeMin(sizeMin);
		setSizeMax(sizeMax);
	}

	protected void instantiate(Object id) {

		if (history == null) {
			History tempHis = (History) History.getHistory(id); // try to get history from table
			if (tempHis != null) { //	if not found in static table
				history = tempHis; // create new History
			} else {
				history = createConcreteHistory(id);
				History.registrateNewHistory(history); // and put it to table
			}
		}
	}

	protected abstract History createConcreteHistory(Object id);


	public History getHistory() {
		return history;
	}

	public synchronized void setId(Object id) {
		if (this.id == null) {
			this.id = id;
			instantiate(id);
		}
	}
	public Object getId() {
		return id;
	}
	public int getSizeMin() {
		return sizeMin;
	}
	public void setSizeMin(int sizeMin) {
		this.sizeMin = sizeMin;
		history.setSizeMin(sizeMin);
	}
	public int getSizeMax() {
		return sizeMax;
	}
	public void setSizeMax(int sizeMax) {
		this.sizeMax = sizeMax;
		history.setSizeMax(sizeMax);
	}
}